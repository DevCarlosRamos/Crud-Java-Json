package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String JSON_FILE = System.getProperty("user.dir") + "\\src\\main\\resources\\data.json";
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        // Create some initial data
        List<Usuario> users = new ArrayList<>();
        users.add(new Usuario(0, "Carlos"));
        users.add(new Usuario(1, "Luz"));
        users.add(new Usuario(2, "Daniel"));

        // Write initial data to JSON file
        writeToJson(users);

        // Read data from JSON file
        List<Usuario> loadedUsers = readFromJson();
        System.out.println("lista de usuarios agregados:");
        for (Usuario user : loadedUsers) {
            System.out.println(user);
        }

        // Update user
        Usuario userToUpdate = loadedUsers.get(0);
        userToUpdate.setName("cualquier cosa");
        updateUser(userToUpdate);

        // Read updated data from JSON file
        List<Usuario> updatedUsers = readFromJson();
        System.out.println("Updated users from JSON:");
        for (Usuario user : updatedUsers) {
            System.out.println(user);
        }

        // Delete user
        deleteUser(updatedUsers.get(0));

        // Read final data from JSON file
        List<Usuario> finalUsers = readFromJson();
        System.out.println("Final users from JSON:");
        for (Usuario user : finalUsers) {
            System.out.println(user);
        }
    }

    private static List<Usuario> readFromJson() {
        try (FileReader reader = new FileReader(JSON_FILE)) {
            Type type = new TypeToken<List<Usuario>>() {
            }.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static void writeToJson(List<Usuario> users) {
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateUser(Usuario userToUpdate) {
        List<Usuario> users = readFromJson();
        for (Usuario user : users) {
            if (user.getId() == userToUpdate.getId()) {
                user.setName(userToUpdate.getName());
                break;
            }
        }
        writeToJson(users);
    }

    private static void deleteUser(Usuario userToDelete) {
        List<Usuario> users = readFromJson();
        users.removeIf(user -> user.getId() == userToDelete.getId());
        writeToJson(users);
    }

    static class Usuario {
        private int id;
        private String name;

        public Usuario(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Usuario{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}