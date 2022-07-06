import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

class Utils {

    public User userToObj(StringBuffer response) {
        String userToObj = String.valueOf(response.replace(0, 3, "").replace(response.length() - 1, response.length(), ""));
//        User user = new Gson().fromJson(userToObj, User.class);
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String json = gson.toJson(user);
//        System.out.println(json);
        return new Gson().fromJson(userToObj, User.class);
    }

    public int getLastPost(String url, int userId) throws IOException {
        URL url1 = new URL(String.format(url + userId + "/posts"));
        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();
        StringBuffer response = new StringBuffer();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } else {
            System.out.println("GET request not worked");
        }

        String[] list = response.toString().split(",\\s+");
        String listOfIds = "";
        for (String s : list) {
            if (s.matches("\"[a-z]{2}\":\\s\\d+")) {
                listOfIds += s + ", ";
            }
        }
        String[] ids = listOfIds.split(", \"id\": ");
        return Integer.parseInt(ids[ids.length - 1].replace(", ", ""));
    }

    public void createJsonFile(int userId, int postMaxId, StringBuffer response) {
        final File OUTPUT_FILE_PATH = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + String.format("user-%d-post-%d-comments.json", userId, postMaxId));
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_FILE_PATH.getAbsolutePath()))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String comments = gson.toJson(String.valueOf(response));
            bufferedWriter.write(comments);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public List<User> getMultipleUsers(StringBuffer response) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<User>>(){}.getType();
//        List<User> list = gson.fromJson(String.valueOf(response), type);
//        Gson gs = new GsonBuilder().setPrettyPrinting().create();
//        String json = gs.toJson(list);
//
//        System.out.println(json);
        return gson.fromJson(String.valueOf(response), type);
    }

    public List<Comment> getMultipleComments(StringBuffer response) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Comment>>(){}.getType();
//        List<Comment> list = gson.fromJson(String.valueOf(response), type);
//        Gson gs = new GsonBuilder().setPrettyPrinting().create();
//        String json = gs.toJson(list);
//
//        System.out.println(json);
        return gson.fromJson(String.valueOf(response), type);
    }

    public List<ToDo> getMultipleToDos(StringBuffer response) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<ToDo>>(){}.getType();
//        List<ToDo> list = gson.fromJson(String.valueOf(response), type);
//        Gson gs = new GsonBuilder().setPrettyPrinting().create();
//        String json = gs.toJson(list);
//
//        System.out.println(json);
        return gson.fromJson(String.valueOf(response), type);
    }
}