# Enhancing Search Experience on SAP Hybris (SAP CX)
This project aims for extending solr search delivered OOTB with SAP Hybris. This will majorly includes following:-
* content search
* pdf search
* Category Suggestions Enhancements.

Much More


Just to have a quick glance refer the video on following Link.
https://www.useloom.com/share/7f91e3a0c136422bb560bebe143f3c48

Please ignore the UI for now !! will improve the same over the period of time.





### Version Summary ####

Application | Version  
----------- | --------
Java         | 1.8
Hybris.      | 6.3 +

### Java Installation ###

* Install the JDK software
 * Download and install the required version from http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
 * For OSX, download the .dmg version and run the install wizard.
    The installation directory is: /Library/Java/JavaVirtualMachines/
    The installation folder can also be retrieved by running: /usr/libexec/java_home -v 1.8

#### Set JAVA_HOME ####

##### Windows #####
* Right click My Computer and select Properties.
* On the Advanced tab, select Environment Variables, and then edit JAVA_HOME to point to where the JDK software is located.

##### MAC #####
* Edit (create) the .profile file in your home directory and add: *export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)*
* Verify by running in a terminal window: *echo $JAVA_HOME*


### Source Code Setup ###

* Download the code from the shared codebase
* Download the package in your local into  <repo_directory>/
+ Create sym-link (using command line)

```

Link from <working_directory>/hybris/bin/custom/enhancedSearch to <repo_directory>/custom/enhancedSearch
Link from <working_directory>/hybris/bin/custom/enhancedSearchAddon to <repo_directory>/custom/enhancedSearchAddon

```

#### Windows Steps ####
```

  cd <working_directory>\hybris\bin\custom
  mklink /J enhancedSearch <repo_directory>/custom/enhancedSearch
  mklink /J enhancedSearchAddon <repo_directory>/custom/enhancedSearchAddon

```

#### Mac Steps ####

```
  cd <working_directory>/hybris/bin/custom
  ln -s <repo_directory>/custom/enhancedSearch
  ln -s <repo_directory>/custom/enhancedSearchAddon

```

#### Include EnhancedSearch Project ####
```

  Open localextension.xml - <working_directory>/hybris/config/localextension.xml
  Add Following extension 
  <extension name='enhancedSearchAddon'/>
  <extension name='enhancedSearch'/>

```

#### Project setup ####
* Navigate to *<working_directory>/hybris/bin/platform* from the command prompt and run.

#### Windows: ####

```
   setantenv.bat

```

#### OSX: ####


```
 . ./setantenv.sh

```


* Run ant clean all
* Run ant addoninstall -Daddonnames="enhancedSearchAddon" -DaddonStorefront.yacceleratorstorefront="yacceleratorstorefront"

* Initialize the platform

### Option 1 ###
  * Start the hybris server:
#### Windows: ####

```
    /bin/plateform/hybrisserver.bat

```
#### OSX: #####

```
    /bin/plateform/hybrisserver.sh

```

  * Open the web browser and go to https://localhost:9002/


    User name: admin

    
    Password: nimda


    Click on Initialize

### Option 2: ###

```
  ant initialize

```  




* Hosts file changes
  Modify your system's hosts file to add the following entries

```
    127.0.0.1       electronics.local

```


