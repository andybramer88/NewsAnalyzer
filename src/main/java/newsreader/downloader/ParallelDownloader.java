package newsreader.downloader;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelDownloader<ThreadCallable> extends Downloader{
    @Override
    public int process(List<String> urls) throws IOException {
        long timer = System.currentTimeMillis();
        int count = 0;


        List <Future<String>> futures = new ArrayList<>();

        for(String url : urls){
            if(url == null){
                continue;
            }
            newsreader.downloader.ThreadCallable urlT = new newsreader.downloader.ThreadCallable(this, url);
            ExecutorService executor = Executors.newCachedThreadPool();
            futures.add(executor.submit(urlT));
            // ThreadCallable urlT = new ThreadCallable(this, url);
            // futures.add(executor.submit(urlT));
         }

        for(Future<String> future : futures){
            try{
                String fileName = future.get();
                if(fileName != null)
                    count++;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println("Parallel-Timer: " +(System.currentTimeMillis() - timer));
        return count;
    }
}
