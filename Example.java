package waterLevelProject;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

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

class WaterTankController{
    private WaterLevelFrame waterLevelFrame;
    private AlarmFrame alarmFrame;
    private SpliterFrame spliterFrame;

    private int waterTankLevel;

    public void setAlarmFrame(AlarmFrame alarmFrame){
        this.alarmFrame = alarmFrame;
    }

    public void setDisplayFrame(WaterLevelFrame waterLevelFrame){
        this.waterLevelFrame = waterLevelFrame;
    }

    public void setSplitterFrame(SpliterFrame splitterFrame){
        this.spliterFrame = splitterFrame;
    }

    public void checkValue(int value){
        if (this.waterTankLevel != value){
            this.waterTankLevel = value;
            setWaterLevel();
        }
    }

    public void setWaterLevel(){
        this.alarmFrame.setShowAlarm(waterTankLevel);
        this.waterLevelFrame.setShowWaterLevel(String.valueOf(waterTankLevel));
        this.spliterFrame.setShowAlarm(waterTankLevel);
    }
}


class WaterLevelFrame extends JFrame {
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

    public void setShowWaterLevel(String value){
        this.showWaterLevel.setText(value);
    }
    public JLabel getShowWaterLevel(){
        return this.showWaterLevel;
    }
}

class AlarmFrame extends JFrame {
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

    public void setShowAlarm(int value) {
            this.showAlarm.setText(value>75? "On" : "Off");
    }
}

class SpliterFrame extends JFrame {
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

    public JLabel getShowAlarm() {
        return showSpliter;
    }

    public void setShowAlarm(int value) {
        this.showSpliter.setText(value>90 ? "On" : "Off");

    }
}

public class Example {
    public static void main(String[] args) {
        WaterTankController wtc = new WaterTankController();
        wtc.setAlarmFrame(new AlarmFrame());
        wtc.setDisplayFrame(new WaterLevelFrame());
        wtc.setSplitterFrame(new SpliterFrame());
        WaterTankFrame w1 = new WaterTankFrame(wtc);

    }
}
