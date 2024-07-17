package waterLevelProject;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

class WaterLevelObserver extends JFrame{
    public void updates(int waterLevel){
        //
    }
}

class WaterTankFrame extends JFrame{
    private JSlider waterLevelSlider;

    WaterTankFrame(WaterTankController waterTankController){
        setSize(300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Water Tank");
        setLocationRelativeTo(null);

        this.waterLevelSlider = new JSlider(JSlider.VERTICAL);
        this.waterLevelSlider.setMajorTickSpacing(50);
        this.waterLevelSlider.setPaintLabels(true);
        add(waterLevelSlider);

        this.waterLevelSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = waterLevelSlider.getValue();
                waterTankController.checkValue(value);
            }
        });

        setVisible(true);
    }
}

class SmsSender extends WaterLevelObserver {
    private JLabel showAlarm;

    SmsSender(){
        setSize(300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setTitle("SMS Sender");

        this.showAlarm = new JLabel("");
        this.showAlarm.setFont(new Font("",1,10));
        add(showAlarm);

        setVisible(true);
    }

    public void updates(int value) {
        this.showAlarm.setText("SMS Sender : " + value);
    }
}

class WaterLevelFrame extends WaterLevelObserver {
    private JLabel showWaterLevel;

    WaterLevelFrame() {
        setSize(300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Water Thank Level");

        this.showWaterLevel = new JLabel("50");
        this.showWaterLevel.setFont(new Font("",2,100));
        add(showWaterLevel);

        setVisible(true);
    }

    public void updates(int value) {
        this.showWaterLevel.setText(String.valueOf(value));
    }
    public JLabel getShowWaterLevel(){
        return this.showWaterLevel;
    }
}

class AlarmFrame extends WaterLevelObserver {
    private JLabel showAlarm;

    AlarmFrame(){
        setSize(300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
//        setLayout(new FlowLayout(FlowLayout.CENTER));
        setTitle("Alarm Frame");

        this.showAlarm = new JLabel("Off");
        this.showAlarm.setFont(new Font("",1,100));
        add(showAlarm);

        setVisible(true);
    }

    public JLabel getShowAlarm() {
        return showAlarm;
    }

    public void updates(int value) {
        this.showAlarm.setText(value>75? "On" : "Off");
    }
}

class SpliterFrame extends WaterLevelObserver {
    private JLabel showSpliter;

    SpliterFrame(){
        setSize(300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Spliter Frame");


        this.showSpliter = (new JLabel("Off"));
        this.showSpliter.setFont(new Font("",1,100));
        add(showSpliter);

        setVisible(true);
    }

    public void updates(int value) {
        this.showSpliter.setText(value>90 ? "On" : "Off");
    }
}

class WaterTankController{
    private WaterLevelFrame waterLevelFrame;
    private AlarmFrame alarmFrame;
    private SpliterFrame spliterFrame;
    private SmsSender smsSenderFrame;
    private WaterLevelObserver[] observers = new WaterLevelObserver[0];

    private int waterTankLevel;

    public void addWaterLevelObserver(WaterLevelObserver levelObserver){
        WaterLevelObserver[] temp = new WaterLevelObserver[observers.length+1];
        for (int i=0; i<observers.length; i++){
            temp[i] = observers[i];
        }
        temp[temp.length-1] = levelObserver;
        observers = temp;
    }

    public void checkValue(int value){
        if (this.waterTankLevel != value){
            this.waterTankLevel = value;
            setWaterLevel();
        }
    }

    public void setWaterLevel(){
        for(WaterLevelObserver waterLevelObserver : observers ){
            waterLevelObserver.updates(waterTankLevel);
        }
    }
}

public class Example {
    public static void main(String[] args) {
        WaterTankController wtc = new WaterTankController();
        wtc.addWaterLevelObserver(new AlarmFrame());
        wtc.addWaterLevelObserver(new SpliterFrame());
        wtc.addWaterLevelObserver(new WaterLevelFrame());
        wtc.addWaterLevelObserver(new SmsSender());
        WaterTankFrame w1 = new WaterTankFrame(wtc);

    }
}
