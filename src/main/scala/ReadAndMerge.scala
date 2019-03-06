import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, FileUtil, Path}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, SQLContext, functions}

object ReadAndMerge {

  val PATH = "src\\main\\resources\\"
  var FILENAME = "glasses.csv"
  var OUTPUT_FILE_NAME = PATH + "/temp_" + FILENAME
  var MERGED_FILE_NAME = PATH + "/preprocessed_" + FILENAME

  var conf = new SparkConf().setAppName("Read CSV File").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)
  import sqlContext.implicits._

  def main(args: Array[String]): Unit = {
    val filteredReportDf = getReportDataFrame()
      .where($"activity_type" === "Walk" || $"activity_type" === "Train")
      .orderBy($"from")
    val joinedGlassAndReportByDate = getGlassesDataFrame().join(functions.broadcast(filteredReportDf))
      .where($"DATE" >= $"from" && $"DATE" <= $"to")

    writeDataFrameToFile(OUTPUT_FILE_NAME, MERGED_FILE_NAME, joinedGlassAndReportByDate);
  }

  def mergeToCsvFile(srcPath: String, dstPath: String): Unit =  {
    val hadoopConfig = new Configuration()
    val hdfs = FileSystem.get(hadoopConfig)
    FileUtil.copyMerge(hdfs, new Path(srcPath), hdfs, new Path(dstPath), true, hadoopConfig, null)
  }

  def getReportDataFrame() : DataFrame = {
    sqlContext.read.format("com.databricks.spark.csv")
      .option("treatEmptyValuesAsNulls", "true")
      .option("header", "true")
      .option("inferSchema", "true")
      .option("dateFormat", "yyyy-MM-dd HH:mm")
      .option("mode","DROPMALFORMED")
      .load(PATH + "report.csv")
  }

  def getGlassesDataFrame() : DataFrame = {
    sqlContext.read.format("com.databricks.spark.csv")
      .option("treatEmptyValuesAsNulls", "true")
      .option("header", "true")
      .option("inferSchema", "true")
      .option("dateFormat", "yyyy/MM/dd HH:mm:ss.SSS")
      .option("mode","DROPMALFORMED")
      .load(PATH + "glasses.csv")
  }

  def writeDataFrameToFile(outputFileName: String, mergedFileName : String, dataFrame: DataFrame) : Unit = {
    val mergeFindGlob = outputFileName

    dataFrame.write
      .format("com.databricks.spark.csv")
      .option("header", "false")
      .mode("overwrite")
      .save(outputFileName)
    mergeToCsvFile(mergeFindGlob, mergedFileName)
    dataFrame.unpersist()
  }
}
