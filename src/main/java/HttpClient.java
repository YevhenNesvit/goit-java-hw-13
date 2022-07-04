import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

public class HttpClient {
    private static final String TEST_USERS = "https://jsonplaceholder.typicode.com/users/";
    private static final String TEST_POSTS = "https://jsonplaceholder.typicode.com/posts/";

    public static void createUser() throws IOException {
        URL url = new URL(TEST_USERS);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        OutputStream os = connection.getOutputStream();
        os.write(Files.readAllBytes(new File("src" + File.separator + "main" + File.separator + "resources"
                + File.separator + "userToCreate.json").toPath()));
        os.flush();
        os.close();

        int responseCode = connection.getResponseCode();
        System.out.println("POST response code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);
        } else {
            System.out.println("POST request not worked");
        }
    }

    public static void updateUser() throws IOException {
        int userId = 2;
        URL url = new URL(TEST_USERS + userId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setConnectTimeout(2000);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        OutputStream os = connection.getOutputStream();
        os.write(Files.readAllBytes(new File("src" + File.separator + "main" + File.separator + "resources"
                + File.separator + "userToUpdate.json").toPath()));
        os.flush();
        os.close();

        int responseCode = connection.getResponseCode();
        System.out.println("PUT response code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);
        } else {
            System.out.println("PUT request not worked");
        }
    }

    public static void deleteUser() throws IOException {
        int userId = 2;
        URL url = new URL(TEST_USERS + userId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("DELETE");

        int responseCode = connection.getResponseCode();
        System.out.println("DELETE response code: " + responseCode);
    }

    public static void getAllUsers() throws IOException {
        URL url = new URL(TEST_USERS);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();
        System.out.println("GET response code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);
        } else {
            System.out.println("GET request not worked");
        }
    }

    public static void getUserById() throws IOException {
        URL url = new URL(String.format(TEST_USERS + "?id=%d", 2));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();
        System.out.println("GET response code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);
        } else {
            System.out.println("GET request not worked");
        }
    }

    public static void getUserByUserName() throws IOException {
        URL url = new URL(String.format(TEST_USERS + "?username=%s", "Karianne"));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();
        System.out.println("GET response code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);
        } else {
            System.out.println("GET request not worked");
        }
    }

    public static void getCommentsToLastPost() throws IOException {
        int userId = 2;
        URL url1 = new URL(String.format(TEST_USERS + userId + "/posts"));
        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode1 = connection.getResponseCode();
        System.out.println("GET response1 code: " + responseCode1);
        StringBuffer response1 = new StringBuffer();
        if (responseCode1 == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response1.append(inputLine);
            }
            in.close();
        } else {
            System.out.println("GET request not worked");
        }

        String[] list = response1.toString().split(",\\s+");
        String listOfIds = "";
        for (int i = 0; i < list.length; i++) {
            if (list[i].matches("\"[a-z]{2}\":\\s\\d+")) {
                listOfIds += list[i] + ", ";
            }
        }
        String[] ids = listOfIds.split(", \"id\": ");
        int maxId = Integer.parseInt(ids[ids.length - 1].replace(", ", ""));

        URL url2 = new URL(String.format(TEST_POSTS + maxId + "/comments"));
        connection = (HttpURLConnection) url2.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode2 = connection.getResponseCode();
        System.out.println("GET response2 code: " + responseCode2);
        StringBuffer response2 = new StringBuffer();
        if (responseCode2 == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response2.append(inputLine);
            }
            in.close();
        } else {
            System.out.println("GET request not worked");
        }
        final File OUTPUT_FILE_PATH = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + String.format("user-%d-post-%d-comments.json", userId, maxId));
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_FILE_PATH.getAbsolutePath()))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String comments = gson.toJson(response2);
            bufferedWriter.write(comments);
            bufferedWriter.flush();
        }
    }

    public static void getToDos() throws IOException {
        int userId = 2;
        URL url = new URL(String.format(TEST_USERS + userId + "/todos?completed=%b", false));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();
        System.out.println("GET response code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);
        } else {
            System.out.println("GET request not worked");
        }
    }
}