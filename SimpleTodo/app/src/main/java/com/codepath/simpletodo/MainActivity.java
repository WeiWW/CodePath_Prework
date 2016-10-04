package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final int REQEST_CODE = 0;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItem);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
        editListViewListener();
    }

    private void editListViewListener()
    {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = items.get(position);
                Intent intent = new Intent(MainActivity.this,EditItemActivity.class);
                Bundle bundle =new Bundle();
                bundle.putInt("position",position);
                bundle.putString("text",text);
                intent.putExtra("extras",bundle);
                startActivityForResult(intent,REQEST_CODE);
            }
        });
    }

    private void setupListViewListener()
    {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }
    public void onAddItem(View view)
    {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    private void readItems(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");

        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<>();
        }
    }

    private void writeItems()
    {
        File filewDir = getFilesDir();
        File todoFile = new File(filewDir,"todo.txt");

        try {
            FileUtils.writeLines(todoFile,items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == REQEST_CODE && resultCode == RESULT_OK)
        {
            Bundle bundle = data.getBundleExtra("extra");
            String editedItem = bundle.getString("text");
            int position = bundle.getInt("position");
            items.set(position,editedItem);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }
}
