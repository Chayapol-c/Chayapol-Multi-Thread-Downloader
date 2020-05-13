# Multi-Thread Downloader

FlashGet is a multi-threaded file downloader. 
It uses multiple threads to download parts of a file in parallel. 
You can choose the directory where files will go to. FlashGet is user-friendly. 
In addition, You just put the URL in then press the “Download” button. 
You can also press the “Cancel” button to cancel a download at any time. 
FlashGet has a simple Ul. It shows the downloaded file name ,remaining time and it’s progress.

### Usage
[![interface](https://user-images.githubusercontent.com/59835553/81732906-5f7c5380-94bb-11ea-883a-6bd47a6752a0.png)](![interface](https://user-images.githubusercontent.com/59835553/81732906-5f7c5380-94bb-11ea-883a-6bd47a6752a0.png))  

number 1-7 show multi-thread downloader's features.       

* **1**  Textfield used for input your url.  

* **2**  "Download" button used for download a file.  

* **3**  "Clear" button used for clear input field.

* **4**  download progressbar shows download progress which is how many bytes downloaded.

* **5**  "Cancel" button used for cancel current downloading.

* **6**  remaining time in downloading.

* **7**  thread progressbar show download progress for each thread. 

#### How to run Downloader
1. Run .jar file by command line using this Syntax.  
    > java --module-path "javafxPATH" --add-module javafx.controls,javafx.fxml -jar flashget.jar
2. After open , enter your url to field. 

3. Click Download button. If thing you enter isn't url app will pop-up window will appear. 

4. Choose your directory. then click save.

5. Wait for downloading. Window will popped-out when download is finished



### UML Diagram
[![Package flashget](https://user-images.githubusercontent.com/59835553/81727846-c6960a00-94b3-11ea-835e-0c45daa4d596.png)](https://user-images.githubusercontent.com/59835553/81727846-c6960a00-94b3-11ea-835e-0c45daa4d596.png)


