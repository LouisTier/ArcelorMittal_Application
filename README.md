# ArcelorMittal : FIRST 

## **1. About this project**

Everything is explained in our different files: *'DesignFile'*, *'Specifications'* or *'UserGuide'* within the *Reports* folder. However everything is written in French. To simplify and summarize our project, feel free to read this README!

Here are the details of each file: 

  - **'Specifications'** is simply the **introduction to the subject**. It explains where such a project comes from and what it will be used for
  - **'DeisgnFile'** allows external people to **understand the application** that has been developed, its components and all the relationships
  - **'UserGuide'** is simply a **notice** that allows the user to **take over and use the application**.

Besides the reports, we have other files:

  - **'Diagrams'** which simply contains all the diagrams and graphics designed for the design file or the specifications
  - **'ProjectsDetails'** contains a more detailed presentation of the problem as well as a presentation of **Orowan** which is the mathematical model used to calculate different quantities from the collected data
  - **'rugo'** which is our application with the different classes, the resources used, our database or the executable

This project was done in a school context with three other classmates.

## **2. The context**

ArcelorMittal is a **leader among the world's steel groups**, more particularly in production, notably by being the world's leading steel producer in 2018 with nearly *100 million tons* produced. In order to **optimize production** and **improve product quality**, ArcelorMittal proposes the FIRST project in partnership with IMT Mines Alès.

Summarizing the production steps, the process ends with a rolling step during which the input metal blocks are transformed into metal coils after being heated, resized in width and thickness, then cooled to obtain the quality and mechanical properties and finally wound to obtain the desired structure.

<div id="process" align = "center">
  <img src="https://user-images.githubusercontent.com/105392989/176406142-c3d0fce8-721c-4c57-b55e-0988d548e073.png" width="900">
</div>

During this process, the metal may **degrade and show imperfections** on the surface. Once the imperfections appear, it is not possible to **use and sell** the material, so it is discarded or considered defective.   
In order to limit these degradations, it is **necessary to lubricate correctly and at the right time** the system in order to avoid damaging the metal plates to ensure quality production and material, and thus improve yield.

The **FIRST project** was created to meet this main need.   
The objective is to **collect a set of data** *(every 200ms)* from sensors and **store them in a database** in order to **calculate** with the help of mathematical models proposed by ArcelorMittal, output values corresponding to **different magnitudes** essential to the optimization of production **including the coefficient of friction** to adjust the lubrication.   
To make the data understandable and usable for the users *(technicians, administrator)*, it is also necessary to **display the evolution in real time** 
of the output variables, in particular the coefficient of friction, and **store them once again in a database**.

## **3. Our proposal : Rugo**

Here is the **general architecture** of Rugo:

<div id="general architecture" align = "center">
  <img src="https://user-images.githubusercontent.com/105392989/176406334-2aa6aae0-47d0-4d73-898d-6b2412175c55.png" width="900">
</div>

If you want more details about each class, feel free to look at the class diagrams!   
We find a classic **model-view-controller** *(MVC)* architecture to which we have added a new package : *"data_types"* which will allow us to clearly **define the data** that we process as well as the associated instances.

In chronological order, we have four types of data:

  1. The *'SensorOutData'* which correspond to the raw data directly from the sensors of each production workshop
  2. The *'DonneInOrowan'* which correspond to the information kept among the 'DonneOutCapteur' in order to be used for the calculation that Orowan carries out
  3. The *'DonneOutOrowan'* which are the output data of Orowan, therefore post-calculation. In particular, we obtain the rheology, the speed of the roll and the coefficient of friction as requested with the inverse calculation method *(Back)*.
  4. The *'DisplayData'* which are simply the same quantities as *'OrowanData'* but we average them every second (usually over 5 values)

We store our data in several tables through a **relational database SQL**. More precisely, here is the schema of our database with the different tables:

<div id="databases schema" align = "center">
  <img src="https://user-images.githubusercontent.com/105392989/176406740-c1d76c7f-3bfe-497c-8d7b-0695e72d84db.png" width="900">
</div>

These tables represent **all the data used** by our system during the process.   
We store all the values used and calculated in different tables in order to **re-use them later** in particular for the graphic display or simply to **keep a trace of the progress** of our application.

Same remark as for the class diagrams, if you want to understand in more detail the chronological functioning of our application, I invite you to look at the sequence diagrams!

## **4. How to use it**

First, here is a list of **prerequisites** : 

  - Install the latest version (8.1.4) of XAMPP with the MySQL module (apache server server to access a MySQL database): https://www.apachefriends.org/fr/index.html
  - Install the latest version of the Eclipse development interface (4.21.0): https://www.eclipse.org/downloads/packages/installer
  - Have at least the following version of Java: java version '1.8.0_311', Java (TM) SE Runtime Environment (build 1.8.0_311-b11), Java HotSpot(TM) 64-Bit Server VM (build 25.311-b11, mixed mode)
  - Operating system: Windows 10
  - Download 7-Zip File Manager to unzip .rar folders: https://www.7-zip.fr/

Now, in order to execute our application correctly, here is the procedure to follow:

  - 1 : Launch xampp
  - 2: Click on the *'start'* button in front of Apache
  - 3: Click on the *'start'* button in front of MySQL
  - 4: Click on the *'admin'* button in front of MySQ

<div id="step 1" align = "center">
  <img src="https://user-images.githubusercontent.com/105392989/176406918-0000dc91-f2e2-40b3-be6f-76a7e901ff8f.png" width="650">
</div>

  - 5: Click on the *'Import'* tab
  - 6 : Click on the button *'Choose a file'* and import the document *'db.sql'* from the zipped file 'rugo.rar' (which must be unzipped: use 7-Zip File Manager)
  - 7: Click on the *'Run'* button

<div id="step 2" align = "center">
  <img src="https://user-images.githubusercontent.com/105392989/176407000-c70564f6-5a01-451d-9139-f0f4a86eaee2.png" width="650">
</div>

  - 8 : Select the *'rugo'* DB
  - 9 : Click on the *'Privileges'* tab
  - 10 : Select *'Add a new user'*

<div id="step 3" align = "center">
  <img src="https://user-images.githubusercontent.com/105392989/176407063-ea38edfd-3ecf-4bc4-b8a8-97ab2aa2aec5.png" width="650">
</div>

  - 11 : Enter the code *'gettersetter'* in the user name field
  - 12 : Enter the code *'gettersetter'* in the password field
  - 13 : Click on the *'Run'* button

<div id="step 4" align = "center">
  <img src="https://user-images.githubusercontent.com/105392989/176407130-72ab29c2-c113-4058-af57-5aad7aaec610.png" width="650">
</div>

  - 14 : Double click on *'start.bat'* in the 'rugo' folder to launch the application
  - 14 bis : Open a command console and write the following command : ```java -jar rugo.jar```

**Congratulations**, you can now use our application. Normally the interface below should have appeared on your screen.

<div id="interface" align = "center">
  <img src="https://user-images.githubusercontent.com/105392989/176407240-ce7f5eea-c5a6-4391-95fe-24ef57fcacd6.png" width="500">
</div>

Once the application is launched, you will reach the **login screen**. 
Starting from an empty initial configuration, it is possible to connect via two accounts, namely a **technician** or a **process engineer**:

  - Technician: log = **user** | pw = **user**
  - Process engineer: log = **admin** | pw = **admin**

<div id="connection" align = "center">
  <img src="https://user-images.githubusercontent.com/105392989/176407362-96f611bf-ce86-4887-b93d-f1aad5ba9d51.png" width="500">
</div>

Depending on your status, you have two choices:

  - The **display of graphs** and the evolution of certain quantities
  
  <div id="graphs" align = "center">
    <img src="https://user-images.githubusercontent.com/105392989/176407505-26d9a16b-24e2-488b-a040-25da1c11b700.png" width="500">
  </div>
  
  - The **management of the users**
  
  <div id="users management" align = "center">
    <img src="https://user-images.githubusercontent.com/105392989/176407598-f04d6ba7-4c0d-47cb-b03f-df141c557e4e.png" width="500">
  </div>

## **4. Suggestions for improvement**

Now, in relation to our ideas, we have some proposals for improvement in order to focus on a more "professional" project:

  - First of all, a **graphic redesign**. Indeed, the functionalities requested in the specifications are well respected and we also proposed additional services *(alerts in case of error, encryption, etc...)* but the graphical interface remains **primitive** and only served us as a support to check the proper functioning of our application. However, it should not be neglected at all.   
  A good use and a good understanding from the users are **essential to save time**, **limit the training hours**s and thus **improve the productivity and potentially the return**. All this requires a **fluid and visually pleasing graphical interface**
  - **Discussing with the company** to establish essential assumptions for the proper functioning of the system as well as **scheduling meetings** to present the progress regularly *(AGILE method)*
  - **Break down our DAO into several DAOs**. More precisely, one DAO per data type (referring to the *data_types* package) to avoid conflicts and facilitate management. Also we could have proposed a table for each production workshop in order to avoid storing the stand number at each call as we do.   
  We can also discuss the creation of our *'data_orowan_in'* table which only retrieves certain values without any processing of the table *'donnee_capteurs_out'*
  - **Provide an additional** or different protection service. Indeed, our relatively basic *(MD5)* encryption can lead to confusion in the system, especially with the creation of duplicates, and it is easy to circumvent such an encryption system nowadays
  - Set up a **'super administrator'** to **facilitate the management of the database** user in case of cleaning or internal problems in the company
