package com.ddyos.unicode.exifinterface.sample;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.ddyos.unicode.exifinterface.UnicodeExifInterface;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends Activity {

    private static final String FILE_NAME = "test.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copyAssets();
        initViews();
    }

    private void initViews() {

        findViewById(R.id.btn_write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UnicodeExifInterface unicodeExifInterface = new UnicodeExifInterface(getPhotoPath());
                    unicodeExifInterface.setAttribute(UnicodeExifInterface.TAG_USER_COMMENT, "ýÄÑ123中文Englishにほんご");
                    unicodeExifInterface.saveAttributes();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UnicodeExifInterface unicodeExifInterface = new UnicodeExifInterface(getPhotoPath());
                    String userComment = unicodeExifInterface.getAttribute(UnicodeExifInterface.TAG_USER_COMMENT);
                    ((TextView)findViewById(R.id.tv_show)).setText(userComment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private String getPhotoPath() {
        return getFolderPath() + FILE_NAME;
    }

    private String getFolderPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "unicode_exif" + File.separator;
    }

    private void copyAssets() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {
            if (!filename.equals(FILE_NAME)) {
                continue;
            }
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                String path = getFolderPath();
                File folder = new File(path);
                folder.mkdirs();
                File outFile = new File(path, filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

}