package org.pragyan.pragyantshirtapp;

import android.content.SharedPreferences;
import android.graphics.Color;

public class Utilities {


    public static String[] strcolors={"#BF360C","#F4511E","#FF7043","#EF6C00","#FB8C00","#FFA726","#FFA000","#FFC107","#FFD54F","#FDD835",
            "#FFEE58","#F4FF81","#DCE775","#CDDC39","#AED581","#8BC34A","#689F38","#4CAF50",
            "#00C853","#00E676","#1DE9B6","#00BFA5","#00B8D4","#0091EA","#0288D1","#01579B","#1565C0",
            "#5C6BC0","#3949AB","#7E57C2","#512DA8","#311B92","#6A1B9A","#8E24AA","#AB47BC","#CE93D8","#D500F9","#FF4081",
            "#EC407A","#D81B60","#AD1457","#880E4F"};
    public static int colors[];
    public static int offset;
    public static SharedPreferences sp;
                                            //Fields in Shared Preferences getSharedPreferences("llep")
                                                //1. offset [For main landing page]
                                                //2. status
                                                    // 0 - Not logged in/registered --> redirect to loginActivity
                                                    // 1 - Logged in                --> redirect to MainActivity
                                                //3. pragyan_mail
                                                //4. pragyan_pass
                                                //5. name
                                                //6. pid
                                                //7. fullname
                                                //8. gcm_registered
    public static int status = 0;
    public static int gcm_registered = 0;
    public static String pragyan_mail;
    public static String pragyan_pass;
    public static String name;
    public static String fullname;
    public static int pid;
    public static int screenHeight;
    public static int screenWidth;

    public static String url_auth = "https://api.pragyan.org/user/auth";
    public static String url_details = "https://api.pragyan.org/user/getDetails";
    public static String url_reg = "https://api.pragyan.org/user/register";
    public static String url_event_details = "https://api.pragyan.org/user/getEvents";
    public static String url_qr = "http://api.pragyan.org/tshirt/qrcode";
    public static String url_gcm = "https://pragyan.org/simple-gcm/register.php";

    public static String[] strcolorsEvents={"#BF360C","#F4511E","#FF7043","#EF6C00","#FB8C00","#FFA726","#FFA000","#FFC107"
            ,"#DCE775","#CDDC39","#AED581","#8BC34A","#689F38","#4CAF50",
            "#00C853","#00E676","#1DE9B6","#00BFA5","#00B8D4","#0091EA","#0288D1","#01579B","#1565C0",
            "#5C6BC0","#3949AB","#7E57C2","#512DA8","#311B92","#6A1B9A","#8E24AA","#AB47BC","#CE93D8","#D500F9","#FF4081",
            "#EC407A","#D81B60","#AD1457","#880E4F"};

    public static String contacts = "[{\"event_id\": \"1\", \"event_name\": \"IOT Challenge\", \"contact_name\": \"K.Vignesh Ram\", \"contact_no\": \"7598051440\"}, {\"event_id\": \"2\", \"event_name\": \"Greenville\", \"contact_name\": \"Taveen\", \"contact_no\": \"9791380408\"}, {\"event_id\": \"3\", \"event_name\": \"Inspinature\", \"contact_name\": \"Seshasayee\", \"contact_no\": \"7200851635\"}, {\"event_id\": \"4\", \"event_name\": \"Pragyan Quiz\", \"contact_name\": \"Prithvi \", \"contact_no\": \"9600390225\"}, {\"event_id\": \"5\", \"event_name\": \"Aerogami\", \"contact_name\": \"Deepak V\", \"contact_no\": \"8903494784\"}, {\"event_id\": \"6\", \"event_name\": \"Memory challenge\", \"contact_name\": \"Ganesh S\", \"contact_no\": \"8754307892\"}, {\"event_id\": \"7\", \"event_name\": \"Fix It\", \"contact_name\": \"Srujan\", \"contact_no\": \"9629606785\"}, {\"event_id\": \"8\", \"event_name\": \"Fox hunt\", \"contact_name\": \"Manideep\", \"contact_no\": \"9543056695\"}, {\"event_id\": \"9\", \"event_name\": \"Sanrachana\", \"contact_name\": \"Manohar\", \"contact_no\": \"8220275688\"}, {\"event_id\": \"10\", \"event_name\": \"Snakes and Ladders\", \"contact_name\": \"Nithil Harris\", \"contact_no\": \"9600918084\"}, {\"event_id\": \"11\", \"event_name\": \"Puzzlemania\", \"contact_name\": \"Ajay Panth\", \"contact_no\": \"9095886327\"}, {\"event_id\": \"12\", \"event_name\": \"Science Solitaire\", \"contact_name\": \"Sasi\", \"contact_no\": \"9791271190\"}, {\"event_id\": \"13\", \"event_name\": \"Hunt the Code\", \"contact_name\": \"Manu Agrawal\", \"contact_no\": \"8015816642\"}, {\"event_id\": \"14\", \"event_name\": \"Threes a crowd\", \"contact_name\": \"Shreyas\", \"contact_no\": \"9840935466\"}, {\"event_id\": \"15\", \"event_name\": \"GPU Coding\", \"contact_name\": \"Prakash\", \"contact_no\": \"8122349590\"}, {\"event_id\": \"16\", \"event_name\": \"Game Dev\", \"contact_name\": \"Rachit Rajat\", \"contact_no\": \"9198598292\"}, {\"event_id\": \"17\", \"event_name\": \"Code character\", \"contact_name\": \"Juan Mathews\", \"contact_no\": \"8281342318\"}, {\"event_id\": \"18\", \"event_name\": \"Adaventure\", \"contact_name\": \"Palash\", \"contact_no\": \"8940970271\"}, {\"event_id\": \"19\", \"event_name\": \"Pragyan CTF\", \"contact_name\": \"Harsha\", \"contact_no\": \"9789070029\"}, {\"event_id\": \"20\", \"event_name\": \"Bytecode\", \"contact_name\": \"Naresh Krishna\", \"contact_no\": \"9487009499\"}, {\"event_id\": \"21\", \"event_name\": \"Circuitrix\", \"contact_name\": \"Naga Sai\", \"contact_no\": \"9505843648\"}, {\"event_id\": \"22\", \"event_name\": \"Delta T\", \"contact_name\": \"Sameer Raza Mohammad\", \"contact_no\": \"9600774306\"}, {\"event_id\": \"23\", \"event_name\": \"NFS aqua\", \"contact_name\": \"Nikita Manth\", \"contact_no\": \"9159772703\"}, {\"event_id\": \"24\", \"event_name\": \"Hoverone\", \"contact_name\": \"Ganesh C\", \"contact_no\": \"9840824561\"}, {\"event_id\": \"25\", \"event_name\": \"PDC\", \"contact_name\": \"Chendur S\", \"contact_no\": \"9790933882\"}, {\"event_id\": \"26\", \"event_name\": \"Hydro\", \"contact_name\": \"Kathiravan S\", \"contact_no\": \"9500150893\"}, {\"event_id\": \"27\", \"event_name\": \"Junkyard wars\", \"contact_name\": \"Sai Aravindh\", \"contact_no\": \"9840950476\"}, {\"event_id\": \"28\", \"event_name\": \"Water Rocket\", \"contact_name\": \"Shiva Manohar\", \"contact_no\": \"9791449432\"}, {\"event_id\": \"29\", \"event_name\": \"NITTro\", \"contact_name\": \"Karthik Ramakrishnan\", \"contact_no\": \"9445612673\"}, {\"event_id\": \"30\", \"event_name\": \"QUADcombat\", \"contact_name\": \"Manikanta\", \"contact_no\": \"9600930229\"}, {\"event_id\": \"31\", \"event_name\": \"Mortar Master\", \"contact_name\": \"mike\", \"contact_no\": \"9942224738\"}, {\"event_id\": \"32\", \"event_name\": \"Aakriti\", \"contact_name\": \"karthik\", \"contact_no\": \"9791392062\"}, {\"event_id\": \"33\", \"event_name\": \"Topographya\", \"contact_name\": \"harish\", \"contact_no\": \"7305894929\"}, {\"event_id\": \"34\", \"event_name\": \"Beer factory\", \"contact_name\": \"Koushik\", \"contact_no\": \"8122224157\"}, {\"event_id\": \"35\", \"event_name\": \"Pragyan Premier League\", \"contact_name\": \"Gokul\", \"contact_no\": \"9003037906\"}, {\"event_id\": \"36\", \"event_name\": \"Dalal Street\", \"contact_name\": \"Rishi\", \"contact_no\": \"8056083211\"}, {\"event_id\": \"37\", \"event_name\": \"The Ultimate manager\", \"contact_name\": \"Shilpa\", \"contact_no\": \"8015157265\"}, {\"event_id\": \"38\", \"event_name\": \"Marketing Hub\", \"contact_name\": \"Ramakrishna\", \"contact_no\": \"9840524567\"}, {\"event_id\": \"39\", \"event_name\": \"M Decoder\", \"contact_name\": \"Suraj Kiran\", \"contact_no\": \"8939467563\"}, {\"event_id\": \"40\", \"event_name\": \"String Theory\", \"contact_name\": \"Pratibha\", \"contact_no\": \"9442674653\"}, {\"event_id\": \"42\", \"event_name\": \"Crime busters\", \"contact_name\": \"Sreeraj\", \"contact_no\": \"9787149663\"}, {\"event_id\": \"43\", \"event_name\": \"Labyrinth\", \"contact_name\": \"Shreyas\", \"contact_no\": \"9840935466\"}, {\"event_id\": \"44\", \"event_name\": \"Fundamental\", \"contact_name\": \"George\", \"contact_no\": \"8220275752\"}, {\"event_id\": \"45\", \"event_name\": \"Rush Hour\", \"contact_name\": \"Lokesh Kumar\", \"contact_no\": \"8903781031\"}, {\"event_id\": \"46\", \"event_name\": \"Seven Stones\", \"contact_name\": \"Sidhaarth\", \"contact_no\": \"9566197260\"}, {\"event_id\": \"47\", \"event_name\": \"Clash of Pirates\", \"contact_name\": \"Sabarish\", \"contact_no\": \"9962202047\"}, {\"event_id\": \"48\", \"event_name\": \"Pengufest\", \"contact_name\": \"Sam\", \"contact_no\": \"9952868058\"}, {\"event_id\": \"49\", \"event_name\": \"Star Gazing\", \"contact_name\": \"Anurag Iyer\", \"contact_no\": \"7373546953\"}]";


    public static void init_colors()
    {
        colors=new int[strcolors.length];
        for(int i=0;i<strcolors.length;i++)
        {
            colors[i]=Color.parseColor(strcolors[i]);
        }
    }

}
