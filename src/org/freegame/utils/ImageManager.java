
package org.freegame.utils;

import java.net.URI;

import org.freegame.game.GameActor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;

/**
 * @author kuno
 *
 */
public class ImageManager {
	
	public static int memoryAvailable;
	private static LruCache<String, Bitmap> mMemoryCache;
	private static Context context;
	public static void Initialize(Context ctx){
		context=ctx;
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		// Use 1/8th of the available memory for this memory cache.
	    final int cacheSize = maxMemory / 8;
		 mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
		        @Override
		        protected int sizeOf(String key, Bitmap bitmap) {
		            return bitmap.getByteCount() / 1024;
		        }
		    };
	}
	/**
	 * this method add a bitmap to the cache
	 * receives the key and the Bitmap to be cached 
	 * */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
	    if (getBitmapFromMemCache(key) == null) {
	        mMemoryCache.put(key, bitmap);
	    }
	}
	/**
	 * this method return a bitmap loaded in the cache with the given key
	 * */
	public Bitmap getBitmapFromMemCache(String key) {
	    return mMemoryCache.get(key);
	}
	
	/**
	 * this method load the images from resources in a asyncktask
	 * */
	public void LoadBitmapFromRes(int resId, GameActor actor) {
	    final String imageKey = String.valueOf(resId);

	    final Bitmap bitmap = getBitmapFromMemCache(imageKey);
	    if (bitmap != null) {
	        actor.ActualTexture=bitmap;
	    } else {
	        BitmapWorkerTask task = new BitmapWorkerTask(actor);
	        task.execute(resId);
	    }
	}
	
	/**
	 * this method load the images from Uri in a asyncktask
	 * */
	public void LoadBitmapFromURI(URI res, GameActor actor) {
	    final String imageKey =res.toString();

	    final Bitmap bitmap = getBitmapFromMemCache(imageKey);
	    if (bitmap != null) {
	        actor.ActualTexture=bitmap;
	    } else {
	    	BitmapWorkerFromURLTask task = new BitmapWorkerFromURLTask(actor);
	        task.execute(imageKey);
	    }
	}
	/**
	 * this class loads the image in background and put in the memory pool also
	 * set it in the scene actor
	 * */
	class BitmapWorkerTask extends AsyncTask<Integer, Void, String> {
		GameActor actor;
		public boolean pending=true;
		public BitmapWorkerTask(GameActor act){ actor=act;	}
	    //...
	    // Decode image in background.
	    @Override
	    protected String doInBackground(Integer... params) {
	        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), params[0]);
	        addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
	        actor.ActualTexture=bitmap;
	        pending=false;
	        return String.valueOf(params[0]);
	    }
	}
	/**
	 * this class loads the image in background and put in the memory pool also
	 * set it in the scene actor
	 * */
	class BitmapWorkerFromURLTask extends AsyncTask<String, Void, String> {
		GameActor actor;
		public boolean pending=true;
		public BitmapWorkerFromURLTask(GameActor act){actor=act;}
	    //...
	    // Decode image in background.
	    @Override
	    protected String doInBackground(String... params) {
	        final Bitmap bitmap = BitmapFactory.decodeFile(params[0]);
	        addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
	        actor.ActualTexture=bitmap;
	        pending=false;
	        return String.valueOf(params[0]);
	    }
	}
}
