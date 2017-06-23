package ir.ap.team37.apgamecontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;

public class ControllerActivity extends AppCompatActivity {

    private String IP;
    private int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IP = getIntent().getStringExtra("ip");
        port = getIntent().getIntExtra("port", 1377);
        (findViewById(R.id.up)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send("up");
            }
        });
        (findViewById(R.id.down)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send("down");
            }
        });
        (findViewById(R.id.left)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send("left");
            }
        });
        (findViewById(R.id.right)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send("right");
            }
        });
        (findViewById(R.id.zoomInBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send("zoomIn");
            }
        });
        (findViewById(R.id.zoomOutBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send("zoomOut");
            }
        });
        (findViewById(R.id.message)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send(((EditText) findViewById(R.id.message)).getText().toString());
                ((EditText) (findViewById(R.id.message))).setText("");
            }
        });
    }

    private void send(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket;
                try {
                    Log.i("ip", IP);
                    socket = new Socket(IP, port);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message);
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
