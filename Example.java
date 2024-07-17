package waterLevelProject;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

class WaterLevelObserver extends JFrame{
    public void update(int waterLevel){
        //
    }
}

class DisplayFrame extends WaterLevelObserver{
    private JLabel displayLabel;

    DisplayFrame(){
        setSize(300, 300);
        setLocationRelativeTo(null);
        setTitle("Display");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        this.displayLabel = new JLabel("50");
        this.displayLabel.setFont(new Font("", 1, 30));
        add(displayLabel);
        setVisible(true);
    }

    public void update(int waterLevel){
        this.displayLabel.setText(Integer.toString(waterLevel));
    }
}

class AlarmFrame extends WaterLevelObserver{
    private JLabel alarmLabel;
    AlarmFrame(){
        setSize(300, 300);
        setLocationRelativeTo(null);
        setTitle("Alarm");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        this.alarmLabel = new JLabel("Off");
        this.alarmLabel.setFont(new Font("", 1, 30));
        add(alarmLabel);

        setVisible(true);
    }

    public void update(int waterLevel){
        this.alarmLabel.setText(waterLevel >=75? "On" : "Off");
    }
}

class SplitterFrame extends WaterLevelObserver{
    private JLabel splitterLabel;

    SplitterFrame(){
        setSize(300, 300);
        setLocationRelativeTo(null);
        setTitle("Splitter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        this.splitterLabel = new JLabel("Off");
        this.splitterLabel.setFont(new Font("", 1, 30));
        add(splitterLabel);

        setVisible(true);
    }

    public void update(int waterLevel){
        this.splitterLabel.setText(waterLevel >=90 ? "On" : "Off");
    }
}

class WaterTankController{
    private WaterLevelObserver[] observers = new WaterLevelObserver[0];

    private int waterLevel;

    public void addWaterLevelObserver(WaterLevelObserver levelObserver){
        WaterLevelObserver[] temp = new WaterLevelObserver[observers.length + 1];
        for(int i = 0; i < observers.length; i++){
            temp[i] = observers[i];
        }
        temp[temp.length-1] = levelObserver;
        observers = temp;
    }

    public void setWaterLevel(int waterLevel){
        if(this.waterLevel != waterLevel){
            this.waterLevel = waterLevel;
            notifyObject();
        }
    }

    public void notifyObject(){
        for(WaterLevelObserver waterLevelObserver : observers){
            waterLevelObserver.update(waterLevel);
        }
    }
}

class WaterTankFrame extends JFrame{

    private JSlider waterLevelSlider;
    private int waterLevel = 0;
    private WaterTankController waterTankController;


    WaterTankFrame(WaterTankController waterTankController){
        this.waterTankController = waterTankController;
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Water Tank");
        setLocationRelativeTo(null);

        this.waterLevelSlider = new JSlider(JSlider.VERTICAL);
        this.waterLevelSlider.setMajorTickSpacing(10);
        this.waterLevelSlider.setPaintLabels(true);
        this.waterLevelSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                waterLevel = waterLevelSlider.getValue();
                waterTankController.setWaterLevel(waterLevel);
            }
        });

        add(waterLevelSlider);

        setVisible(true);
    }

}
class Example{
    public static void main(String[] args) {
        WaterTankController controller = new WaterTankController();
        controller.addWaterLevelObserver(new AlarmFrame());
        controller.addWaterLevelObserver(new SplitterFrame());
        controller.addWaterLevelObserver(new DisplayFrame());
        WaterTankFrame f1 = new WaterTankFrame(controller);
    }
}
