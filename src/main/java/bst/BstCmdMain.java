package bst;

import com.akdeniz.googleplaycrawler.DownloadData;
import de.onyxbits.raccoon.cli.Play;
import de.onyxbits.raccoon.db.DatabaseManager;
import de.onyxbits.raccoon.repo.AndroidApp;
import de.onyxbits.raccoon.repo.AndroidAppDao;
import de.onyxbits.raccoon.repo.Layout;

import java.util.List;

public class BstCmdMain {

    private static void testExcuteDbTask() throws Exception {
        DatabaseManager database = new DatabaseManager(Layout.DEFAULT.databaseDir);
        //List<PlayProfile> playProfileList = database.get(PlayProfileDao.class).list();
        //System.out.println(playProfileList);

        //init some app info to local at the first time //androidAppDao.delete();
        Play.bstInitApp("com.google.android.gms");
        Play.bstInitApp("com.bilibili.fatego"); //404
        Play.bstInitApp("com.aniplex.fategrandorder.en");
        Play.bstInitApp("studio.gameberry.murimrpg"); //korea
        Play.bstInitApp("com.elysium.kingdom.hero.manga.impact.mmorpg.rpg.fun.game");
        Play.bstInitApp("com.facebook.orca");
        Play.bstInitApp("com.supercell.brawlstars");
        Play.bstInitApp("com.pearlabyss.blackdesertm.gl");




        //begin check update for apps from local database
        AndroidAppDao androidAppDao = database.get(AndroidAppDao.class);
        while (true){
            List<AndroidApp> androidAppList = androidAppDao.list();
            int count = 0 ;
            for (AndroidApp androidApp : androidAppList)
            {
                count++;
                String packageName = androidApp.getPackageName();
                int localVerCode = androidApp.getVersionCode();
                String localVer = androidApp.getVersion();
                System.out.println(count+"=>"+packageName+" "+localVerCode+" "+localVer);

                boolean isNeedUpdate = Play.bstCheckAndUpdateApkInfo(packageName,localVer,localVerCode);
                if(isNeedUpdate){
                    System.out.println(packageName+" need update,begin update...");
                    //Play.downloadApp(packageName,-1,1);
                    DownloadData data = Play.bstGetDownloadUrl(packageName,-1,1);
                    if(data != null){
                        System.out.println( data.getAppSize()+ ""+data.getDownloadUrl());
                    }
                    Thread.sleep(5*1000);
                }
                Thread.sleep(5*1000);
            }
            System.out.println("----------------------------");
            Thread.sleep(5*1000);
        }
        //database.shutdown();
    }
}
