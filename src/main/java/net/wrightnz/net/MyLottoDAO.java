/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wrightnz.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Thread.State;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Richard Wright richard@wrightnz.net
 */
public class MyLottoDAO {

    /**
     * Execute a query against the remote database.
     *
     * @return containing the rows in the response
     * @throws MalformedURLException
     * @throws IOException
     */
    private URL getURL() throws MalformedURLException {
        URL url = new URL("https://mylotto.co.nz/");
        return url;
    }

    /**
     * Main method for unit testing only
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        MyLottoDAO dao = new MyLottoDAO();
        List<Integer> numbers = dao.getLatestNumbers();
        for (Integer number : numbers) {
            System.out.println(number);
        }
    }

    public List<Integer> getLatestNumbers() throws IOException {
        final String particalNumberClassName = "resultsBall ballNumber-";
        final List<Integer> numbers = new ArrayList<Integer>();

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URLConnection connection = getURL().openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    try {
                        String line;
                        boolean start = false;
                        while ((line = reader.readLine()) != null) {
                            if (line.contains("resultPowerBall")) {
                                break;
                            }
                            if (line.contains("resultLottoWinnerNumbers")) {
                                start = true;
                            }
                            if (start && line.contains(particalNumberClassName)) {
                                int i = line.indexOf(particalNumberClassName) + particalNumberClassName.length();
                                String numberStr = line.substring(i, i + 2);
                                numbers.add(Integer.parseInt(numberStr));
                            }
                        }
                    } finally {
                        reader.close();
                    }
                } catch (IOException e) {
                    numbers.add(0);
                } catch (NumberFormatException e) {
                    numbers.add(1);
                }
            }
        });
        thread.start();
        while (thread.getState() != State.TERMINATED){
        }
        return numbers;
    }

}
