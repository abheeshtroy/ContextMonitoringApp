# ContextMonitoringApp
This is the comprehensive explanation to my mobile computing project 1 in which my goal was to create an android application to measure heart rate, respiratory rate, log symptoms and store the data in a local database in the android emulator.

Q1 Imagine you are new to the programming world and not proficient enough in coding. But, you have a brilliant idea where you want to develop a context-sensing application like Project 1.  You come across the Heath-Dev paper and want it to build your application. Specify what Specifications you should provide to the Health-Dev framework to develop the code ideally.

-> 1	Sensor specification:
      1.1	Specify the types of sensors we need. For example, accelerometer, GPS etc. 
      1.2	Define the sampling frequency for each sensor.
      1.3	Specify the sensor platform such as TelosB, Shimmer, Arduino etc.
   2	Sensor Subcomponents:
      2.1	Computation: Specify the algorithms needed for processing sensor data. For example, location estimation.
      2.2	Communication: Define the communication protocol, ie., Bluetooth or Zigbee and whether it is single-hop or multi-hop.
   3	Sensor Connections:
      3.1	Specify the execution sequence of algorithms. An example of this step is : when we get some raw data from some sensor, say accelerometer, we extract its features and then to the activity classification.
      3.2	Define what data should be transmitted to the network.
   4	Network Specification:
      4.1	Specify the network topology, including the routing paths.
      4.2	Define any energy management scheme and choose a mode of radio operations.
      4.3	Provide routing information if we are using multi-hop communication.
      5	Smart Phone Specification:
   5.1	Define the UI elements:
        5.1.1	Buttons like start/stop sensing, change settings etc.
        5.1.2	Text views like display current activity, location etc.
        5.1.3	Graphs to visualize sensor data.
    5.2	Specify which sensor nodes can communicate with the smartphone.
    5.3	Define any algorithms to be run on the smartphone.
   6	Smartphone connections
      6.1	Specify how sensor data should be displayed.
      6.2	Define button actions like start/stop sensing, change sampling rates.
   7	Additional Parameters
      7.1	Specify any energy efficiency requirements.
      7.2	Define reliability requirements for communication.
   8	Algorithm selection
      8.1	health monitoring: We can use peak detection and fourier transform for signal processing and radio duty cycling for energy efficiency.
      8.2	We can use machine learning algorithms like decision trees for activity detection.
      8.3	We can use statistical analysis and correlation algorithms for environmental sensing.




Q2 In Project 1 you have stored the user’s symptoms data in the local server. Using the bHealthy application suite how can you provide feedback to the user and develop a novel application to improve context sensing and use that to generate the model of the user? 

-> 1.	Integrate physiological sensors:
      1.1	Use ECG, EEG and accelerometer sensors as described in bHealthy to collect physiological data alongside the symptoms data already stored in the local server.
    2.	Develop an assessment application:
      2.1	Use the collected physiological data to assess the user’s mental and physical state.
      2.2	Incorporate Emotiv’s affectiv algorithms to derive mental states like boredom, excitement etc. form EEG signals
      2.3	Use BSNBench algos to extract heart rate, heart rate variability and other parameters.
      2.4	Combine the information with the symptoms data to get more understanding of the user’s health status.
    3.	Create a context-aware suggestion module:
      3.1	Based on the assessment results and symptoms data, develop an algorithm to suggest appropriate activities for the user.
      3.2	This module can recommend specific lifestyle changes according to the user’s current context and health status.
    4.	Generate wellness reports:
      4.1	Create a reporting system that combines data from the symptoms database, sensors and usage data from the training applications.
    5.	Provide real-time feedback:
      5.1	Implement a notification system that can alert users about changes in their health status and provide timely suggestions.




Q3 A common assumption is mobile computing is mostly about app development. After completing Project 1 and reading both papers, have your views changed? If yes, what do you think mobile computing is about and why? If no, please explain why you still think mobile computing is mostly about app development, providing examples to support your viewpoint

-> Yes, my views about mobile computing have definitely changed. After completing project 1 and reading both papers, it’s clear that mobile computing is much more than just app development. It’s about creating a whole ecosystem that integrates hardware like sensors, software, real-time data processing and user interface.

For instance our project on heart rate sensing uses camera sensors and flashlight for capturing the video, ideally an accelerometer is also used for respiratory rate measurement. A lot of real-time data processing is at play while calculating the heart and respiratory rate. Then the user interface is also a very important part of mobile computing. A local database creation and storage is another aspect that was used in our project.

The Health-Dev paper showed how mobile systems can adapt to changes in the user’s environment, like changing network conditions and power management, which requires more than just building an app. It involves automated code generation and managing the complexities of different hardware and communication protocols. On the other hand, the bHealthy app suite focuses on context awareness by using data from sensors like EEG and ECG to give real-time feedback to users, adapting to their mental and physical state. 

So, mobile computing now seems to be about combining various technologies to provide adaptive and personalized services. It is also about understanding user context, integrating sensors, processing data and then using all this to create a system that goes beyond just a simple app interface. 





