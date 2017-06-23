package ir.ap.team37.apgamecontroller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ActivityFindHosts extends AppCompatActivity {

    private String IP = "192.168.43.218";
    private int port = 1377;
    private ListView listView;
    private ArrayList<String> hosts = new ArrayList<>();
    private boolean isEnded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_device);
        try {
            listView = (ListView) findViewById(R.id.list);
            checkHosts();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void checkHosts() throws IOException, InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeout = 100;
                for (int j = 2; j < 255; j++) {
                    final String host = "192.168." + 43 + "." + j;
                    if (isEnded)
                        break;
                    Log.d("host", host);
                    try {
                        if (InetAddress.getByName(host).isReachable(timeout)) {
                            Log.i("reachable", host);
                            hosts.add(host);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    addToList();
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private void addToList() {
        String[] arrays = new String[hosts.size()];
        arrays = hosts.toArray(arrays);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, arrays));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ActivityFindHosts.this, ControllerActivity.class);
                intent.putExtra("ip", hosts.get(position));
                intent.putExtra("port", 1377);
                startActivity(intent);
                isEnded = true;
                finish();
            }
        });
    }

}