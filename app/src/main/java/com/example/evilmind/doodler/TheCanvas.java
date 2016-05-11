package com.example.evilmind.doodler;

import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;


public class TheCanvas extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        SimpleDrawingView sv = (SimpleDrawingView) findViewById(R.id.SimpleDrawingView);
        String path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM).getAbsolutePath();

        Bundle b = getIntent().getExtras();
        if (b!=null) {
            int id = b.getInt("imageNumber");
            if (id != -1) {
                sv.drawFlag = true;

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                String file;
                file = new String(path + "/image" + id + ".png");
                sv.bitmapToDraw = BitmapFactory.decodeFile(file);
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        View content = findViewById(R.id.SimpleDrawingView);
        content.setDrawingCacheEnabled(true);
        content.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = content.getDrawingCache();
        String path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM).getAbsolutePath();
        File file;
        int iter = 0;
        while (true) {
            file = new File(path + "/image" + (iter++) + ".png");
            if (!file.exists()) break;
        }
        FileOutputStream ostream;
        try {
            file.createNewFile();
            ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
            ostream.flush();
            ostream.close();
            Toast.makeText(getApplicationContext(), "image saved", 5000).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "error", 5000).show();
        }
        super.onDestroy();
    }
}
