package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.R.id.edit;

public class EditItemActivity extends AppCompatActivity {

    EditText etItem;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String text ="";

        if (getIntent().hasExtra("extras"))
        {
            Bundle bundle = getIntent().getBundleExtra("extras");
            text = bundle.getString("text");
            position = bundle.getInt("position");
        }

        etItem = (EditText) findViewById(R.id.editText);
        etItem.setText(text);
    }

    public void onSaved(View view)
    {
        String etText = etItem.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("text",etText);
        bundle.putInt("position",position);
        Intent intent = new Intent();
        intent.putExtra("extra",bundle);
        setResult(RESULT_OK,intent);
        finish();
    }

}
