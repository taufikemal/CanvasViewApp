package com.udindev.canvasviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.github.dhaval2404.colorpicker.ColorPickerDialog;
import com.github.dhaval2404.colorpicker.listener.ColorListener;
import com.github.dhaval2404.colorpicker.model.ColorShape;

import adil.dev.lib.materialnumberpicker.dialog.NumberPickerDialog;
import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    MyCanvasView myCanvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myCanvasView= new MyCanvasView(this);
        myCanvasView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        myCanvasView.setStroke(10);
        setContentView(myCanvasView);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.color:
                chooseColor();
                return true;
            case R.id.stroke:
                chooseStroke();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void chooseColor(){
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, R.color.purple_700 , new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
               myCanvasView.setColor(color);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                // cancel was selected by the user
            }

        });
        dialog.show();

    }

    private void chooseStroke(){
        NumberPickerDialog dialog=new NumberPickerDialog(MainActivity.this, 0, 100, new NumberPickerDialog.NumberPickerCallBack() {
            @Override
            public void onSelectingValue(int value) {
                myCanvasView.setStroke(value);
            }
        });
        dialog.show();
    }


}
