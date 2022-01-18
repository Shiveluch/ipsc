package com.shiveluch.ipsc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Simple UI demonstrating how to connect to a Bluetooth device,
 * send and receive messages using Handlers, and update the UI.
 */
public class MainActivity extends Activity {

    // Tag for logging
    private static final String TAG = "BluetoothActivity";


    // MAC address of remote Bluetooth device
    // Replace this with the address of your own module
    //   private final String address = "37:00:00:00:00:00";//научный бокс
    private final String address = "4D:C9:01:00:81:82";//Контроль доступа
    private final int targetscount=8;
    Activity activity;
    ListView targetlist;
    TextView shoottimer, sensitivity;
    SharedPreferences settings;
    EditText shootcont;
    String code="11010000100111001101000110000011110100001011110100100000110100001010010011010000101101011101000010111101110100001011100011010000101110101101000110000001110100011000001100100000110100001001110011010001100000111101000010111101001000001101000010100100110100001011010111010000101111011101000010111000110100001011101011010001100000011101000110000011001000000011010000110011001011100010000011010000100100011101000110000000110100001011000011010001100000100010110000100000110100011000011111010000101101011101000110000000110100011000001011010000101111101101000010110010110100011000100111010000101110001101000010111101110100001011000000100000110100001011101011010000101100001101000010111010110100001011000011010001100011110010110111010001100000101101000010111110001011000010000011010000101100011101000110001011110100001011101100100000110100001011110111010000101100000010000011010000101101001101000010111101110100011000111111010001100001010010000011010000101100100010000011010000101110101101000010111110110100001011111111010000101100001101000110000111110100001011000011010001100001010010110000100000001100010011010100100000110100011000001011010000101100001101000010111100001000001101000010111101110100001011000000100000110100011000000111010000101111011101000010111110110100011000000011010000101110101101000010110000001000001101000010111101110100001011000011010001100000001101000010110010110100001011000011010000101110111101000110000001110100011000111100101110001000001101000010010111110100001011000011010000101100011101000010110101110100001011011011010000101100001101000010111011001000001101000010111101110100001011000000100000110100001011111011010000101110101101000110000000110100001011000011010000101110001101000010111101110100001011010100100000110100001011001000100000110100001011101011010000101100001101000010111010110100001011111011010000101110010010110111010001100000101101000010111110001000000011001000110110001000001101000010110011110100001011000011010001100000001101000010110000110100001011011000100000110100001011111011010001100000100010000011010000101111011101000010110101110100001011001111010000101111100010110000100000110100001011000000100000110100001011010011010000101100001101000010111011110100011000110011010001100010001101000010110101001000001101000110000001110100001011001011010000101101011101000110000010001011000010000011010000101100101101000010110111110100011000000011010001100010111101000010110010110100011000101100100000110100001011100000100000110100001011111011010001100001111101000010111101110100011000001111010000101110111101000110000001110100011000111100100000001101100011000000100000110100011000001111010000101101101101000010110101001000001101000010111101110100001011010111010000101111111101000010111110110100001011110111010001100011111101000110000010110100001011110111010000101111100010000011010000101100111101000010110100110100001011010100101100001000001101000110000101110100001011111011010000101110111101000010111100110100011000101100100000110100001011001011010000101111101101000010111010110100011000000011010001100000111101000010110011001011000010000011010000101100111101000010111110110100011000000011010000101111101101000010110100001000001101000010110111110100001011000011010000101100011101000110000000110100001011111011010001100010001101000010110101110100001011110111010000101111011101000110001011110100001011100100101100001000001101000010110010001000001101000010010111110100001011111011010000101111011101000010110101001000001101000110000010110100001011000011010000101110101101000010111110110100001011001111010000101111100010000011010000101111011101000010110101001000001101000010110010110100011000000111010001100000101101000110000000110100001011010111010001100001111101000010110000110100001011101100100000110100001011110111010000101110001101000010111010110100001011111011010000101100111101000010110100110100001011000000100000001100010011001100110010001011100010000011010000100100001101000010111101110100001011111011010000101111001101000010110000110100001011101111010000101110001101000010111001001011000010000011010000101111001101000110000011110100011000001011010000101100001101000010111101110100011000001011010000101111101101000010110010001000001101000010111101110100001011010111010001100000100010110000100000110100001011110111010000101111100010000011010000101101011101000110000001110100011000001011010001100011000010000011010001100000011101000010111011110100001011010111010000101101001101000110001011001000001101000010111111110100011000000011010000101110001101000110000001110100011000001111010001100000101101000110000001110100011000001011010000101100101101000010111000110100011000111100100000110100011000000111010001100000101101000010110000110100001011101111010000101110101101000010110101110100011000000011010000101111101101000010110010001011100010000000110000001100000010000011010000100111111101000010111110110100011000001011010000101111101101000010111100001000001101000110000001110100001011110111010000101111101101000010110010110100001011000000100000110100001011110111010000101100000010000011010000101100111101000010110000110100011000000011010000101100001101000010110110001000001101000110000010110100001011000011010000101110101101000010111110110100001011100100100000110100001011011011010000101101010010000011010000101100101101000110001011110100011000100011010000101101011101000010111011001011000010000011010000101100100010000011010000101111011101000110010001110100001011110000100000110100001011010111010001100000011101000110000010110100011000110000100000110100001011110011010000101101011101000110000001110100011000001011010000101111100010000011010000101111101101000010110100110100001011110111010000101111100010000011010001100000101101000010110000110100001011110000100000110100001011010011010000101101011101000110000010110100001011010111010000101110101101000110000010110100001011111011010001100000000010000011010000101101111101000010110000110100011000000111010000101101011101000010111010001000001101000010110000110100001011110111010000101111101101000010111100110100001011000011010000101110111101000110001100110100001011110111010001100000111101000110001110001000001101000010110000110100001011101011010001100000101101000010111000110100001011001011010000101111011101000010111110110100011000000111010001100000101101000110001100001011100010000011010000100111011101000010110000001000001101000010110010110100011000000111010001100011111101000010111010110100001011100011010000101110010010000011010001100000011101000010111011110100011000001111010001100001111101000010110000110100001011100100100000110100001011111111010000101111101101000010111100110100001011010111010001100000101101000010111000110100001011101100100000110100001011110011010000101101011101000110000001110100011000001011010000101101011101000110000111110100001011101011010000101111100010000000111000001101110010000011010001100011011101000110000010110100001011111000101100001000001101000110000010110100011000101100100000110100011000001011010000101111101101000110000111110100001011110111010000101111100010000011010000101111001101000010111000110100001011110011010000101111100010000011010000101111011101000010110101001000001101000010111111110100011000000011010000101111101101000010111001110100001011010011010000101101011101000110001000110100011000110000100000110100001011010111010001100000011101000010111011110100001011100000100000110100011000011111010001100000101101000010111110001011100010000011010000101001111101000010110101110100011000000011010000101101011101000010110111001000001101000010111101110100001011010111010000101110101101000010111110110100011000001011010000101111101101000110000000110100001011111011010000101101010010000011010000101100101101000110000000110100001011010111010000101111001101000110001111001000001101000110000001110100001011110111010000101111101101000010110010110100001011000000100000110100011000000111010000101100101101000010110101110100011000001000100000110100001011001011010000101101111101000110000000110100011000101111010000101100101101000110001011001000001101000010111000001000001101000110001111001000001101000010110010110100001011110111010000101111101101000010110010110100011000110000100000110100001001101011010000101111101101000010111111110100001011000011010001100001111101000010110000110100011000010100101110001000000011000000110000001000001101000010101111001000001101000110001101110100011000001011010000101111100010000011010000101110100010000011010001100001111101000010110101110100001011110011010001100000110010110000100000110100011000010111010000101111101101000110000000110100001011111011010001100010001101000010110101110100001011010100100000110100001011110011010000101101011101000110000001110100011000001011010000101111100010000011010000101101001101000010111011110100011000111100100000110100011000000111010001100001011101000110000000110100001011111011010000101111011101000010110000001000001101000110000101110100011000000011010000101101011101000010111101001000001101000010111010110100011000001011010000101111100010000011010000101111011101000010110000110100001011100111010000101101001101000010110101110100011000001000101100001000001101000010110101110100011000000111010000101110111101000010111000001000001101000010110001110100011000001111010000101101001101000010110101110100011000100011010001100011000010000011010001100000101101000010110000110100001011110000100000110100011000000111010001100001001101000010111110110100011000001011010000101110101101000010110000110100001011100100100000110100001011110011010000101111101101000110001110001000001101000010111100110100001011010111010001100000101101000010111010110100011000001100100000110100001011100011010000101111011101000110000010110100001011010111010001100000001101000010110101110100011000000111010000101111011101000010111110001000001101000010111110110100011000000111010001100000101101000010110000110100001011110111010000101101011101000110000010110100011000000111010001100011110010000011010000101110111101000010111000001000001101000010111110110100001011110111010000101100000010000011010001100000101101000010110000110100001011110000101110";
    ArrayList<String> string=new ArrayList<>();
    ArrayList<ListInfo> shootRes = new ArrayList<>();
    String command;
    SeekBar seekBar;
    Context context;
    AlertDialog.Builder alert;
    AlertDialog dialog;
    RelativeLayout [] shootTargets=new RelativeLayout[targetscount];
    TextView [] shootTargetNames = new TextView[targetscount];
    ListView[] shootTargetsList = new ListView[targetscount];
    ArrayList<TargetStat> targStatus = new ArrayList<>();
    Button onoff, status, shooting, stop,train, setup, startshoottimer, setsens, setshootttimer;
    RelativeLayout statusRL, shootRL, stopRL, trainRL, setupRL;
    ArrayList <ListInfo> [] shootResults = new ArrayList[targetscount];
    boolean isShooting=true;
    Timer shootTimer;
    public int mode=0;
    public int stoptarget=0;
    ToneGenerator beep=new ToneGenerator(AudioManager.STREAM_ALARM,100);
    public final static String PREFNAME = "settings";
    public final static String SENS = "sens";
    public final static String SHOOTDURATION = "sduration";



    // The thread that does all the work
    com.shiveluch.ipsc.BluetoothThread btt;
    ArrayList<TargetStat> shoot = new ArrayList<>();

    // Handler for writing messages to the Bluetooth connection
    Handler writeHandler;

    /**
     * Launch the Bluetooth thread.
     */
    public void shootButtonPressed(View v)
    {
        if (btt!=null)
        {
//            shoot.clear();
//            targetlist.setAdapter(null);
            sendBTT("shooting");
        }
    }

    public void statButtonPressed(View v)
    {
        if (btt!=null)
        {
            sendBTT("shooting#");
        }
    }

    private void isConn(String message)
    {
        Button conn = findViewById(R.id.connectButton);
        conn.setEnabled(false);
        conn.setText("База\nподключена");
        sendToast("Соединение установлено");
        conn.setAlpha(0.5f);
        status.setAlpha(1);
        shooting.setAlpha(1);
        stop.setAlpha(1);
        train.setAlpha(1);
        setup.setAlpha(1);
        status.setEnabled(true);
        shooting.setEnabled(true);
        stop.setEnabled(true);
        train.setEnabled(true);
        setup.setEnabled(true);
        sendBTT("request");
        statusRL.setVisibility(View.VISIBLE);


    }

    private void targetstat(String command)
    {
        //targStatus.clear();
        statusRL.setVisibility(View.VISIBLE);
        for (int i = 0; i < command.length(); i++) {
            if (command.charAt(i) == '0') {
Log.d("Staus", "0");
                targStatus.get(i).target = "Мишень " + (i + 1) + ":";
                targStatus.get(i).status = "Не подключена";
                targStatus.get(i).add = "0";

            }
            if (command.charAt(i) == '1') {
                targStatus.get(i).target = "Мишень " + (i + 1) + ":";
                targStatus.get(i).status = "Подключена";
                targStatus.get(i).add = "1";

            }
        }
        MainListAdapter mainListadapter = new MainListAdapter(context, targStatus, activity);
        targetlist.setAdapter(mainListadapter);
    }

    private void isDisconn()
    {
        Button conn = findViewById(R.id.connectButton);
        conn.setEnabled(true);
        conn.setText("Подключить\nбазу");
        sendToast("Соединение сброшено");
        conn.setAlpha(1);
        status.setAlpha(0);
        shooting.setAlpha(0);
        stop.setAlpha(0);
        train.setAlpha(0);
        status.setEnabled(false);
        shooting.setEnabled(false);
        stop.setEnabled(false);
        train.setEnabled(false);
    }

    public void connectButtonPressed(View v) {
        Log.v(TAG, "Connect button pressed.");
        if (btt != null) {
            Log.w(TAG, "Already connected!");

            return;
        }
        btt = new com.shiveluch.ipsc.BluetoothThread(address, new Handler() {


            @Override
            public void handleMessage(Message message) {

                String s = (String) message.obj;

                Log.d("TAKEN", s);
                if (s.contains("!!!"))
                {
                    command=s.replace("!!!","");
                    if (!command.contains("targ")) {
                        targetstat(command);
                    }

                    if (command.contains("targ") )
                    {
                        if (mode==2 || mode==4)
                        command=command.replace("targ","");
                        command = command.replaceAll("[^0-9]", "");
                        shootMode(command);
                    }

                    if (command.contains("targ") && mode==3)
                    {
                        command=command.replace("targ","");
                        command = command.replaceAll("[^0-9]", "");
                        stopMode(command);
                    }


                }

                if (s.contains("CONNECTED") && !s.contains("DIS"))
                {
                isConn("");
                    //sendBTT("request");
                }

                if (s.contains("DIS"))
                {
                isDisconn();
                }

                if (s.contains("FAILED"))
                {
                    Button conn = findViewById(R.id.connectButton);
                    conn.setEnabled(true);
                    conn.setText("Подключить\nбазу" +
                            "");
                    sendToast("Неудачное подключение, перезапустите приложение");
                }



            }
        });
        writeHandler = btt.getWriteHandler();
        btt.start();
    }

    /**
     * Kill the Bluetooth thread.
     */
    public void disconnectButtonPressed(View v) {
        Log.v(TAG, "Disconnect button pressed.");

        if(btt != null) {
            btt.interrupt();
            btt = null;
        }
    }

    /**
     * Send a message using the Bluetooth thread's write handler.
     */

    public void scanButtonPressed(View v) {
        Log.v(TAG, "Scan button pressed.");

        String data = "scan";

        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);


    }


    Audio audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags
                (WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        context=getApplicationContext();
        settings = getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        Timer timer = new Timer();
        timer.schedule(new UpdateTimeTask(), 0, 10);



        activity=this;
        audio = new Audio(context);
        initShootViews();
        initArrays();
        initVisualElements();

    }

    private void initShootViews() {
        shootTargets[0]=findViewById(R.id.target1);
        shootTargets[1]=findViewById(R.id.target2);
        shootTargets[2]=findViewById(R.id.target3);
        shootTargets[3]=findViewById(R.id.target4);
        shootTargets[4]=findViewById(R.id.target5);
        shootTargets[5]=findViewById(R.id.target6);
        shootTargets[6]=findViewById(R.id.target7);
        shootTargets[7]=findViewById(R.id.target8);
        for (int i=0;i<targetscount;i++)
        {
          //  shootTargets[i] = findViewById(1000084-i);
            shootTargets[i].setVisibility(View.GONE);
        }
        shootTargetNames[0] = findViewById(R.id.shoottarget1name);
        shootTargetNames[1] = findViewById(R.id.shoottarget2name);
        shootTargetNames[2] = findViewById(R.id.shoottarget3name);
        shootTargetNames[3] = findViewById(R.id.shoottarget4name);
        shootTargetNames[4] = findViewById(R.id.shoottarget5name);
        shootTargetNames[5] = findViewById(R.id.shoottarget6name);
        shootTargetNames[6] = findViewById(R.id.shoottarget7name);
        shootTargetNames[7] = findViewById(R.id.shoottarget8name);

            for (int i = 0; i < shootTargetNames.length; i++) {
                shootTargetNames[i].setTextColor(Color.parseColor("#FFDD00"));
            }

            shootTargetNames[0].setOnClickListener(view -> {
                if (mode==3) {
                for (int i = 0; i < shootTargetNames.length; i++) {
                    shootTargetNames[i].setTextColor(Color.parseColor("#FFDD00"));
                }
                shootTargetNames[0].setTextColor(Color.parseColor("#00FF00"));
                stoptarget = 1;}
            });

            shootTargetNames[1].setOnClickListener(view -> {
                if (mode==3) {
                    for (int i = 0; i < shootTargetNames.length; i++) {
                        shootTargetNames[i].setTextColor(Color.parseColor("#FFDD00"));
                    }
                    shootTargetNames[1].setTextColor(Color.parseColor("#00FF00"));
                    stoptarget = 2;
                }
            });
            shootTargetNames[2].setOnClickListener(view -> {
                if (mode==3) {
                    for (int i = 0; i < shootTargetNames.length; i++) {
                        shootTargetNames[i].setTextColor(Color.parseColor("#FFDD00"));
                    }
                    shootTargetNames[2].setTextColor(Color.parseColor("#00FF00"));
                    stoptarget = 3;
                }
            });
            shootTargetNames[3].setOnClickListener(view -> {
                if (mode==3) {
                    for (int i = 0; i < shootTargetNames.length; i++) {
                        shootTargetNames[i].setTextColor(Color.parseColor("#FFDD00"));
                    }
                    shootTargetNames[3].setTextColor(Color.parseColor("#00FF00"));
                    stoptarget = 4;
                }
            });
            shootTargetNames[4].setOnClickListener(view -> {
                if (mode==3) {
                    for (int i = 0; i < shootTargetNames.length; i++) {
                        shootTargetNames[i].setTextColor(Color.parseColor("#FFDD00"));
                    }
                    shootTargetNames[4].setTextColor(Color.parseColor("#00FF00"));
                    stoptarget = 5;
                }
            });
            shootTargetNames[5].setOnClickListener(view -> {
                if (mode==3) {
                    for (int i = 0; i < shootTargetNames.length; i++) {
                        shootTargetNames[i].setTextColor(Color.parseColor("#FFDD00"));
                    }
                    shootTargetNames[5].setTextColor(Color.parseColor("#00FF00"));
                    stoptarget = 6;
                }
            });
            shootTargetNames[6].setOnClickListener(view -> {
                if (mode==3) {
                    for (int i = 0; i < shootTargetNames.length; i++) {
                        shootTargetNames[i].setTextColor(Color.parseColor("#FFDD00"));
                    }
                    shootTargetNames[6].setTextColor(Color.parseColor("#00FF00"));
                    stoptarget = 7;
                }
            });
            shootTargetNames[7].setOnClickListener(view -> {
                if (mode==3) {
                    for (int i = 0; i < shootTargetNames.length; i++) {
                        shootTargetNames[i].setTextColor(Color.parseColor("#FFDD00"));
                    }
                    shootTargetNames[7].setTextColor(Color.parseColor("#00FF00"));
                    stoptarget = 8;
                }
            });



        shootTargetsList[0] = findViewById(R.id.shoottarget1list);
        shootTargetsList[1] = findViewById(R.id.shoottarget2list);
        shootTargetsList[2] = findViewById(R.id.shoottarge3list);
        shootTargetsList[3] = findViewById(R.id.shoottarget4list);
        shootTargetsList[4] = findViewById(R.id.shoottarget5list);
        shootTargetsList[5] = findViewById(R.id.shoottarget6list);
        shootTargetsList[6] = findViewById(R.id.shoottarget7list);
        shootTargetsList[7] = findViewById(R.id.shoottarget8list);
    }

    private void initArrays() {
        for (int i=0;i<targetscount;i++)
        {
            targStatus.add(new TargetStat("Мишень " + (i + 1) + ":","Не подключена", "0"));

        }
    }

    @SuppressLint("ResourceType")
    private void initVisualElements() {
        targetlist=findViewById(R.id.targetslist);
        onoff = findViewById(R.id.connectButton);
        status=findViewById(R.id.statButton);
        shooting = findViewById(R.id.shootButton);
        stop = findViewById(R.id.stopButton);
        train = findViewById(R.id.trainButton);
        statusRL = findViewById(R.id.statusRL);
        shootRL = findViewById(R.id.shootRL);
        stopRL = findViewById(R.id.stopRL);
        trainRL = findViewById(R.id.trainRL);
        setupRL = findViewById(R.id.setupRL);
        statusRL.setVisibility(View.GONE);
        stopRL.setVisibility(View.GONE);
        trainRL.setVisibility(View.GONE);
        shootRL.setVisibility(View.GONE);
        setupRL.setVisibility(View.GONE);
        setup=findViewById(R.id.setupButton);
        shoottimer=findViewById(R.id.shoottimer);
        sensitivity = findViewById(R.id.sensitivity);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(settings.getInt(SENS,0));
        status.setEnabled(false);
        shooting.setEnabled(true);
        stop.setEnabled(false);
        train.setEnabled(false);
        setsens = findViewById(R.id.setsens);
        startshoottimer=findViewById(R.id.startshootbutton);
        shootcont=findViewById(R.id.shootcont);
        setshootttimer = findViewById(R.id.setshoottime);
        shootcont.setText(""+settings.getInt(SHOOTDURATION,0));
        sensitivity.setText("Чувствительность мишеней: "+settings.getInt(SENS,0));
      //  targetstat("01101010");

        setshootttimer.setOnClickListener(view -> {

            SharedPreferences.Editor editor = settings.edit();
            if (shootcont.getText().toString().length()>0)
            editor.putInt(SHOOTDURATION,Integer.parseInt(shootcont.getText().toString()));
            else
                editor.putInt(SHOOTDURATION,0);
            editor.apply();

        });

        setsens.setOnClickListener(view -> {
            int sens=seekBar.getProgress();
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt(SENS,sens);
            editor.apply();
            sendBTT("sens"+(settings.getInt(SENS,0)+150));
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                sensitivity.setText("Чувствительность мишеней: "+i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode=1;
                statusRL.setVisibility(View.VISIBLE);
                stopRL.setVisibility(View.GONE);
                trainRL.setVisibility(View.GONE);
                shootRL.setVisibility(View.GONE);
                setupRL.setVisibility(View.GONE);
                status.setTextColor(Color.parseColor("#33E293"));
                shooting.setTextColor(Color.parseColor("#00FCFC"));
                stop.setTextColor(Color.parseColor("#00FCFC"));
                train.setTextColor(Color.parseColor("#00FCFC"));
                setup.setTextColor(Color.parseColor("#00FCFC"));

                sendBTT("request");
            }
        });





               stop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mode=3;
                        sendBTT("stoptar");
                        startshoottimer.setText("СТАРТ");
                        if (shootTimer!=null)
                        {shootTimer.cancel();
                            shootTimer = null;}
                        shoottimecounter = 0;
                        commontimer = 0;
                        secs=0;
                        mins=0;
                        statusRL.setVisibility(View.GONE);
                       // stopRL.setVisibility(View.VISIBLE);
                        trainRL.setVisibility(View.GONE);
                        shootRL.setVisibility(View.VISIBLE);
                        setupRL.setVisibility(View.GONE);
                        status.setTextColor(Color.parseColor("#00FCFC"));
                        shooting.setTextColor(Color.parseColor("#00FCFC"));
                        stop.setTextColor(Color.parseColor("#33E293"));
                        train.setTextColor(Color.parseColor("#00FCFC"));
                        setup.setTextColor(Color.parseColor("#00FCFC"));
                        for (int i=0;i<targetscount;i++)
                        {
                            String isTargetStat=targStatus.get(i).add;
                            String targetName=targStatus.get(i).target;

                            {
                                if (isTargetStat.equals("1"))
                                {
                                    shootResults[i]=new ArrayList<>();
                                    shootResults[i].add(new ListInfo("Нет попаданий","0","","",""));
                                    shootTargets[i].setVisibility(View.VISIBLE);
                                    shootTargetNames[i].setText(targetName.replace(" :",""));
                                    ShootAdapter adapter = new ShootAdapter(context, shootResults[i],activity);
                                    shootTargetsList[i].setAdapter(adapter);

                                }
                            }

                        }

                    }
                });

        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        mode=4;
                sendBTT("training");
                startshoottimer.setText("СТАРТ");
                if (shootTimer!=null)
                {shootTimer.cancel();
                    shootTimer = null;}
                shoottimecounter = 0;
                commontimer = 0;
                secs=0;
                mins=0;
                        statusRL.setVisibility(View.GONE);
                        stopRL.setVisibility(View.GONE);
                        trainRL.setVisibility(View.GONE);
                        shootRL.setVisibility(View.VISIBLE);
                        setupRL.setVisibility(View.GONE);
                        status.setTextColor(Color.parseColor("#00FCFC"));
                        shooting.setTextColor(Color.parseColor("#00FCFC"));
                       train.setTextColor(Color.parseColor("#33E293"));
                        stop.setTextColor(Color.parseColor("#00FCFC"));
                        setup.setTextColor(Color.parseColor("#00FCFC"));

                for (int i=0;i<targetscount;i++)
                {
                    String isTargetStat=targStatus.get(i).add;
                    String targetName=targStatus.get(i).target;

                    {
                        if (isTargetStat.equals("1"))
                        {
                            shootResults[i]=new ArrayList<>();
                            shootResults[i].add(new ListInfo("Нет попаданий","0","","",""));
                            shootTargets[i].setVisibility(View.VISIBLE);
                            shootTargetNames[i].setText(targetName.replace(" :",""));
                            ShootAdapter adapter = new ShootAdapter(context, shootResults[i],activity);
                            shootTargetsList[i].setAdapter(adapter);

                        }
                    }

                }
                    }
                });


        startshoottimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode == 2)
                {    int shootduration = settings.getInt(SHOOTDURATION, 0);
                if (shootduration == 0) {
                    sendToast("Не установлена продолжительность упраженения");
                    return;
                }
                isShooting = !isShooting;
                beep.startTone(ToneGenerator.TONE_DTMF_0, 500);
                if (!isShooting) {
                    shootTimer = new Timer();
                    startshoottimer.setText("ОСТАНОВИТЬ");
                    shoottimecounter = 0;
                    shootTimer.schedule(new shootTimeTask(), 0, 10);
                    for (int i = 0; i < targetscount; i++) {
                        String isTargetStat = targStatus.get(i).add;
                        String targetName = targStatus.get(i).target;

                        {
                            if (isTargetStat.equals("1")) {
                                shootResults[i] = new ArrayList<>();
                                shootResults[i].add(new ListInfo("Нет попаданий", "0", "", "", ""));
                                shootTargets[i].setVisibility(View.VISIBLE);
                                shootTargetNames[i].setText(targetName.replace(" :", ""));
                                ShootAdapter adapter = new ShootAdapter(context, shootResults[i], activity);
                                shootTargetsList[i].setAdapter(adapter);

                            }
                        }

                    }
                }

                if (isShooting) {

                    startshoottimer.setText("СТАРТ");
                    shootTimer.cancel();
                    shootTimer = null;
                    shoottimecounter = 0;
                    commontimer = 0;
                    secs=0;
                    mins=0;
                }

            }

                if (mode==3)
                {
                    if (stoptarget==0)
                    {
                        sendToast("Не выбрана стоп-мишень");
                        return;
                    }
                    int shootduration = settings.getInt(SHOOTDURATION, 0);
                    if (shootduration == 0) {
                        sendToast("Не установлена продолжительность упраженения");
                        return;
                    }
                    isShooting = !isShooting;
                    beep.startTone(ToneGenerator.TONE_DTMF_0, 500);
                    if (!isShooting) {
                        shootTimer = new Timer();
                        startshoottimer.setText("ОСТАНОВИТЬ");
                        shoottimecounter = 0;
                        shootTimer.schedule(new shootTimeTask(), 0, 10);
                        for (int i = 0; i < targetscount; i++) {
                            String isTargetStat = targStatus.get(i).add;
                            String targetName = targStatus.get(i).target;

                            {
                                if (isTargetStat.equals("1")) {
                                    shootResults[i] = new ArrayList<>();
                                    shootResults[i].add(new ListInfo("Нет попаданий", "0", "", "", ""));
                                    shootTargets[i].setVisibility(View.VISIBLE);
                                    shootTargetNames[i].setText(targetName.replace(" :", ""));
                                    ShootAdapter adapter = new ShootAdapter(context, shootResults[i], activity);
                                    shootTargetsList[i].setAdapter(adapter);

                                }
                            }

                        }
                    }

                    if (isShooting) {
if (shootTimer == null) return;
                        startshoottimer.setText("СТАРТ");
                        shootTimer.cancel();
                        shootTimer = null;
                        shoottimecounter = 0;
                        commontimer = 0;
                        secs=0;
                        mins=0;
                    }

                }

                if (mode==4)
                {
                    isShooting = !isShooting;
                    beep.startTone(ToneGenerator.TONE_DTMF_0, 500);
                    if (!isShooting) {
                        shootTimer = new Timer();
                        startshoottimer.setText("ОСТАНОВИТЬ");
                        shoottimecounter = 0;
                        shootTimer.schedule(new shootTimeTask(), 0, 10);
                        for (int i = 0; i < targetscount; i++) {
                            String isTargetStat = targStatus.get(i).add;
                            String targetName = targStatus.get(i).target;

                            {
                                if (isTargetStat.equals("1")) {
                                    shootResults[i] = new ArrayList<>();
                                    shootResults[i].add(new ListInfo("Нет попаданий", "0", "", "", ""));
                                    shootTargets[i].setVisibility(View.VISIBLE);
                                    shootTargetNames[i].setText(targetName.replace(" :", ""));
                                    ShootAdapter adapter = new ShootAdapter(context, shootResults[i], activity);
                                    shootTargetsList[i].setAdapter(adapter);

                                }
                            }

                        }
                    }

                    if (isShooting) {
                        if (shootTimer == null) return;
                        startshoottimer.setText("СТАРТ");
                        shootTimer.cancel();
                        shootTimer = null;
                        shoottimecounter = 0;
                        commontimer = 0;
                        secs=0;
                        mins=0;
                    }

                }
        }
        });

        setup.setOnClickListener(view -> {
            statusRL.setVisibility(View.GONE);
            stopRL.setVisibility(View.GONE);
            trainRL.setVisibility(View.GONE);
            shootRL.setVisibility(View.GONE);
            setupRL.setVisibility(View.VISIBLE);
            status.setTextColor(Color.parseColor("#00FCFC"));
            shooting.setTextColor(Color.parseColor("#00FCFC"));
            setup.setTextColor(Color.parseColor("#33E293"));
            stop.setTextColor(Color.parseColor("#00FCFC"));
            train.setTextColor(Color.parseColor("#00FCFC"));
        });




        shooting.setOnClickListener(view -> {
              sendBTT("shooting");
              mode=2;
            startshoottimer.setText("СТАРТ");
            if (shootTimer!=null)
            {shootTimer.cancel();
            shootTimer = null;}
            shoottimecounter = 0;
            commontimer = 0;
            secs=0;
            mins=0;
            statusRL.setVisibility(View.GONE);
            stopRL.setVisibility(View.GONE);
            trainRL.setVisibility(View.GONE);
            shootRL.setVisibility(View.VISIBLE);
            setupRL.setVisibility(View.GONE);

            status.setTextColor(Color.parseColor("#00FCFC"));
            train.setTextColor(Color.parseColor("#00FCFC"));
            shooting.setTextColor(Color.parseColor("#33E293"));
            stop.setTextColor(Color.parseColor("#00FCFC"));
            setup.setTextColor(Color.parseColor("#00FCFC"));
            for (int i=0;i<targetscount;i++)
            {
                String isTargetStat=targStatus.get(i).add;
                String targetName=targStatus.get(i).target;

                {
                    if (isTargetStat.equals("1"))
                    {
                        shootResults[i]=new ArrayList<>();
                        shootResults[i].add(new ListInfo("Нет попаданий","0","","",""));
                        shootTargets[i].setVisibility(View.VISIBLE);
                        shootTargetNames[i].setText(targetName.replace(" :",""));
                        ShootAdapter adapter = new ShootAdapter(this, shootResults[i],this);
                        shootTargetsList[i].setAdapter(adapter);

                    }
                }

            }


        });
    }

    /**
     * Kill the thread when we leave the activity.
     */
    protected void onPause() {
        super.onPause();

        if(btt != null) {
            btt.interrupt();
            btt = null;
        }
    }

    class UpdateTimeTask extends TimerTask {

        @Override
        public void run() {


            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                }
            });
        }
    }
    int shoottimecounter=0;
    int commontimer=0;
    int centsec=0;
    int mins = 0;
    int secs = 0;
    String time;
    String minuts = "";
    String seconds = "";
    class shootTimeTask extends TimerTask {

        @Override
        public void run() {

            int shootduration = settings.getInt(SHOOTDURATION,0);
            if (commontimer<shootduration) {
                shoottimecounter++;
                centsec++;
            }

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mins <=9)minuts ="0"+ mins; else minuts=""+ mins;
                    if (secs <=9) seconds ="0"+ secs; else seconds=""+secs;
                    if (shoottimecounter>100)
                    {
                        shoottimecounter=0;
                        secs++;
                        commontimer++;
                        if (commontimer==shootduration && mode!=4)
                        {
                            shootRes.clear();
                            beep.startTone(ToneGenerator.TONE_DTMF_0, 500);
                            isShooting=!isShooting;
                            startshoottimer.setText("СТАРТ");
                            shootTimer.cancel();
                            shootTimer=null;
                            shoottimecounter=0;
                            commontimer=0;
                            secs=0;
                            mins=0;
                            for (int i=0;i<targetscount;i++)
                            {
                                String isTargetStat=targStatus.get(i).add;
                                String targetName=targStatus.get(i).target;

                                {
                                    if (isTargetStat.equals("1"))
                                    {
                                        if (shootResults[i].get(0).field2.equals("0")) return;
                                        int sum=0;
                                        int firstshoot = Integer.parseInt(shootResults[i].get(0).field3);
                                        for (int j=1;j<shootResults[i].size();j++)
                                            {
                                                int ms = Integer.parseInt(shootResults[i].get(j).field3) - Integer.parseInt(shootResults[i].get(j-1).field3);
                                                sum+=ms;
                                            }

                                        shootRes.add (new ListInfo("Мишень "+(i+1), ""+shootResults[i].size(), ""+((sum*10)/shootResults[i].size()),"",""));


                                        Log.d ("Medi","Target "+i+"Первое попадание: "+firstshoot+", Сумма попаданий: "+sum+", Количеств попаданий: "
                                                +shootResults[i].size()+", Среднее значение:" + (sum/shootResults[i].size()));

                                    }
                                }

                            }

                            showShootResultsDialog();

                        }
                        Log.d("secs",""+secs);
                        if (secs>59)
                        {
                            secs=0;
                            mins++;
                        }

                    }


                    shoottimer.setText(""+minuts+":"+seconds+"."+shoottimecounter);
                }
            });
        }
    }

    private void sendBTT(String data)
    {
       {Message msg = Message.obtain();
       msg.obj = data;
       writeHandler.sendMessage(msg);}
    }

    private void sendToast(String mes)
    {
        Toast.makeText(getApplicationContext(),mes,Toast.LENGTH_SHORT).show();
    }

    private void shootMode(String command)
    {
        String data=shoottimer.getText().toString();
        if (data.length()<1) return;
        int target=Integer.parseInt(command);
        if (shootResults[target-1].get(0).field2.contains("0"))
        {
            shootResults[target-1].clear();
        }


        shootResults[target-1].add(new ListInfo(data,"1",""+centsec,"",""));
        ShootAdapter mainListadapter = new ShootAdapter(context, shootResults[target-1], activity);
        shootTargetsList[target-1].setAdapter(mainListadapter);

    }

    private void stopMode(String command)
    {
        String data=shoottimer.getText().toString();
        int target=Integer.parseInt(command);
        if (target!=stoptarget) return;
        if (target==stoptarget){
        startshoottimer.setText("СТАРТ");
        shootTimer.cancel();
        shootTimer = null;
        shoottimecounter = 0;
        commontimer = 0;
        secs=0;
        mins=0;
        showStopDialog(data);

        }
    }

    private void showStopDialog(String time)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alert = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            alert = new AlertDialog.Builder(this);
        }

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.stopdialog, null);


        Button dismiss = view.findViewById(R.id.dismiss);
        TextView text = view.findViewById(R.id.textdialog);
        text.setText("Стоп-мишень поражена\n"+time);
        alert.setView(view);
        alert.setCancelable(false);
        dialog = alert.create();

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }


    private void showShootResultsDialog() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alert = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            alert = new AlertDialog.Builder(this);
        }

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.shootresultsdialog, null);


        Button dismiss = view.findViewById(R.id.dismiss);
        ListView result = view.findViewById(R.id.shootresultdialoglist);

        alert.setView(view);
        alert.setCancelable(false);
        // mess.setText("Удалить сообщение?");
        ShootresultDialogAdapter shootresultDialogAdapter = new ShootresultDialogAdapter(context,shootRes,activity );
        result.setAdapter(shootresultDialogAdapter);
        dialog = alert.create();

      dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }



}


