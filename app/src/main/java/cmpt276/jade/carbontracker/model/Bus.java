package cmpt276.jade.carbontracker.model;

/**
 * Created by Sean on 20/03/2017.
 * Bus object that stores some not so useful data
 */

public class Bus extends ImageId{

    private String nickName;
    private String routeNumber;
    private int mode;
    private int position;

    public Bus() {

    }

    public Bus(String nickName, String routeNumber) {
        this.nickName = nickName;
        this.routeNumber = routeNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDetail(){
        return getNickName() + " - " + getRouteNumber();
    }

    @Override
    public String toString() {
        return "Bus{" +
                "nickName='" + nickName + '\'' +
                ", routeNumber='" + routeNumber + '\'' +
                '}';
    }
}
