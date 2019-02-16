package com.google.cloud.vision.samples;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import com.mysql.jdbc.Connection;

import java.sql.*;
import javax.imageio.ImageIO;


public class GreetingServer {

    private static ServerSocket serverSocket;

    private static Connection con;

    public GreetingServer(int port)  {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Error Creating serverSocket");
            e.printStackTrace();
        }
    }

    public static BufferedImage acceptCommunicationWithWebcamClient(File file) {
        BufferedImage img = null;
        try {
            /**
             *Accept communication with webcam client
             */
            Socket server = serverSocket.accept();
            System.out.println("Recieved Image");


            /**
             * Read Image from Socket and save the image to a file
             */
            img = ImageIO.read(ImageIO.createImageInputStream(server.getInputStream()));
            ImageIO.write(img, "jpg", file);
            System.out.println("Finished Image to File.");


        } catch (Exception e) {
            System.out.println("Error accepting communications and/or writing to file");
        }
        return img;
    }

    public static void commitToDatabase(File file, String url, String user, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(url, user, password);

            con.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not found or DriverManager not init.");
            e.printStackTrace();

        } catch (SQLException sqlException) {
            System.out.println("Error with MySQL");
            sqlException.printStackTrace();
        }


        /**
         * Commit the base-64 encoded image to a file for later use.
         * @apiNote FileInputStream, PreparedStatement.
         * The second parameter index saves as the image.
         */
        String INSERT_PICTURE = "INSERT INTO pic(img) VALUES (?)";

        try (FileInputStream fis = new FileInputStream(file);
             PreparedStatement ps = con.prepareStatement(INSERT_PICTURE)) {
            ps.setBinaryStream(1, fis, (int) file.length());
            ps.executeUpdate();
            con.commit();

        } catch (SQLException sqlException) {
            System.out.println("error with MySQL");
            sqlException.printStackTrace();

        } catch (FileNotFoundException fileNotFound) {
            System.out.println("File is not found of does not exist");
            fileNotFound.printStackTrace();

        } catch (IOException ioException) {
            System.out.println("Error creating file input stream");
            ioException.printStackTrace();
        }
    }

    public static void sendImageToWebsite(String webSiteAddress, int webSitePort, BufferedImage img) {
        System.out.printf("Image trying to send to %s : %d", webSiteAddress, webSitePort);
        try {
            Socket socket = new Socket(webSiteAddress, webSitePort);
            ImageIO.write(img, "JPG", socket.getOutputStream());

        } catch (UnknownHostException e) {
            System.out.println("Could not find host");
            System.out.println();
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("Input/Output error");
            e.printStackTrace();
        }

        System.out.println("Sent image to website");
    }


}