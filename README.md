It's an application that merge glasses.csv context with report.csv file that contains labeled data. Before you use, please add glasses.csv and report.csv files from the address below.

About dataset

Context
The study of human mobility and activities has opened up to an incredible number of studies in the past, most of which included the use of sensors distributed on the body of the subject. More recently, the use of smart devices has been particularly relevant because they are already everywhere and they come with accurate miniaturized sensors. Whether it is smartphones, smartwatches or smartglasses, each device can be used to describe complementary information such as emotions, precise movements, or environmental conditions.

Content
First of all, a smartphone is used to capture mainly contextual data. Two applications are used: a simple data collection application based on the SWIPE open-source sensing system (SWIPE), and a logbook application for obtaining real data on user activity (TimeLogger). SWIPE is a platform for sensing, recording and processing human dynamics using smartwatches and smartphones.

Then, a smartwatch is used primarily to capture the user's heart rate. Motion data is also collected, without being at the heart of the dataset due to its need to be configured with a low sampling frequency, which would drastically increase the dataset and drain the battery as well. An application based on SWIPE is used.

Finally, JINS MEME smartglasses are used. This model has the advantage of being compact and simple to carry. It does not have a camera or a screen; it simply has three types of sensors: an accelerometer (for detecting steps or activities), a gyroscope (for head movements) and an occulographic sensor (eye blinking, eye orientation). The official DataLogger application from JINS MEME is used.

For more information on the dataset please refer to the corresponding publication, available at An Open Dataset for Human Activity Analysis using Smart Devices.

The current dataset on Kaggle contains smartglasses data with 20ms interval (due to storage limitations), same data with 10ms interval is also available on demand. Contact sasan.jafarnejad [at] uni [dot] lu to receive the 10ms version.

Acknowledgements
This work was performed within the eGLASSES project, which is partially funded by NCBiR, FWF, SNSF, ANR and FNR under the ERA-NET CHIST-ERAII framework.

Based on : https://www.kaggle.com/sasanj/human-activity-smart-devices
