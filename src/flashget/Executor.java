package flashget;

import java.io.File;

public class ThreadsGenerate {
    private static ThreadsGenerate instance ;
    private int threadNumber;
    private String urlName;
    private File out;
    private long size;
    private long fileLength;

    private ThreadsGenerate(){
        DownloadTask[] tasks = new DownloadTask[threadNumber];
        for(int i = 0 ; i < threadNumber ; i++){
            tasks[i] = new DownloadTask(urlName, out , size*i ,size);
            if(i == threadNumber-1){
                tasks[i] = new DownloadTask(urlName , out , size*i , fileLength-(size*i));
            }
        }
    }

    public static ThreadsGenerate getInstance(){
        if(instance == null){
            instance = new ThreadsGenerate();
        }
        return instance;
    }

    public void setUrlName(String urlName){
        this.urlName = urlName;
    }

    public void setThreadNumber(int threadNumber){
        this.threadNumber =threadNumber;
    }
    public void setFileLength(long fileLength){
        this.fileLength = fileLength;
    }
    public void setOut(File file){
        this.out = file;
    }

    public void setSize(long size){
        this.size = size;
    }


}
