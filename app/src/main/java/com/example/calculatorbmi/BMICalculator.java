package com.example.calculatorbmi;

import android.os.StrictMode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BMICalculator {

    private static ObjectOutputStream toServer = null;
    private static ObjectInputStream fromServer = null;
    private static Socket socket = null;

    public static double calcBMI(double height, double weight) throws IOException, ClassNotFoundException {
        createClientSocket();
        double[] source = {weight, height};
        toServer.writeObject("calcBMI");
        toServer.writeObject(source);
        double calculatedBmi = (double) fromServer.readObject();
        closeClient();
        return calculatedBmi;

//        public static double calcBMI(double height, double weight) {
//            double bmi;
//            height =  height / 100; // converted to meters
//            bmi = weight / (height * height);
//            int leftDot = (int) bmi;
//            int rightDot = ((int) ((bmi * 100) % 100)); // two numbers after dot
//            double rightRounded = Math.round((double) rightDot / 10); // for example 69=>70 (6.9=>7.0)
//            return (double) leftDot + rightRounded / 10.0;
//        }
    }

    public static String getWeightStatus(double bmi) throws IOException, ClassNotFoundException {
        createClientSocket();
        double resubmit = bmi;
        toServer.writeObject("getWeightStatus");
        toServer.writeObject(resubmit);
        String status = (String) fromServer.readObject();
        closeClient();
        return status;
    }


//        createClientSocket();
//        toServer.writeObject("getWeightStatus");
//        toServer.writeObject(bmi);
//        String getWeightStatusresult = fromServer.toString();
//        return getWeightStatusresult;
//    }
//        String status = "";
//        if (bmi < 15) {
//            status = "Anorexic";
//        } else if (isBetween(bmi, 15, 18.5)) {
//            status = "Underweight";
//        } else if (isBetween(bmi, 18.5, 24.9)) {
//            status = "Normal";
//        } else if (isBetween(bmi, 25, 29.9)) {
//            status = "Overweight";
//        } else if (isBetween(bmi, 30, 35)) {
//            status = "Obese";
//        } else if (bmi > 35) {
//            status = "Extreme Obese";
//        }
//        return status;
//    }


    public static double getIdealWeight(double height, int age, String bodyType) throws IOException, ClassNotFoundException {

        createClientSocket();
        toServer.writeObject("getIdealWeight");
        toServer.writeObject(height);
        toServer.writeObject(age);
        toServer.writeObject(bodyType);
        double getIdealWeight = (double) fromServer.readObject();
        closeClient();
        return getIdealWeight;
    }

//    double height = 1.80;
//    int age = 33;
//    String bodyType = "Large";
//        toServer.writeObject("getIdealWeight");
//        toServer.writeObject(height);
//        toServer.writeObject(age);
//        toServer.writeObject(bodyType);
//    double IdealWeight = (double) fromServer.readObject();
//        System.out.println( " my ideal " + IdealWeight);


//        double slimness = calculateSlimness(bodyType);
//        double idealWeight = (height - 100 + ((double)(age / 10))) * 0.9 * slimness;
//        int leftDot = (int) idealWeight;
//        int rightDot = ((int) ((idealWeight * 100) % 100)); // two numbers after dot
//        double rightRounded = Math.round((double) rightDot / 10); // for example 69=>70 (6.9=>7.0)
//        return (double) leftDot + rightRounded / 10.0;
//    }

    private static boolean isBetween(double x, double lower, double upper) {
        return lower <= x && x <= upper;
    }

    private static double calculateSlimness(String bodyType) {
        double slimness;
        switch (bodyType) {
            case "Small":
                slimness = 0.9;
                break;
            case "Medium":
                slimness = 1;
                break;
            case "Large":
                slimness = 1.1;
                break;
            default:
                slimness = 0;
                break;
        }
        return slimness;
    }

    public static void createClientSocket() throws IOException {
        socket = new Socket("192.168.68.105", 8010);
        toServer = new ObjectOutputStream(socket.getOutputStream());
        fromServer = new ObjectInputStream(socket.getInputStream());
        System.out.println("client: Created Socket");
    }

    public static void closeClient() throws IOException {
        if (socket != null) {
            toServer.writeObject("stop");
            System.out.println("client: Close all streams");
            fromServer.close();
            toServer.close();
            socket.close();
            System.out.println("client: Closed operational socket");
        }
    }
}
