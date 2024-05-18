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

    private static final String url = System.getProperty("user.dir") + "\\src\\main\\resources\\data.json";
    private static final Gson json = new Gson();

    public static void main(String[] args) {

        // Create some initial data
        List<Usuario> ListaDeUsuarios = new ArrayList<>();

        ListaDeUsuarios.add(new Usuario(0, "Carlos"));
        ListaDeUsuarios.add(new Usuario(1, "Luz"));
        ListaDeUsuarios.add(new Usuario(2, "Daniel"));
        ListaDeUsuarios.add(new Usuario(3, "Ramon"));

        // Write initial data to JSON file
        guardarEnELJson(ListaDeUsuarios);

        // Read data from JSON file
        List<Usuario> ListaDeUsuarios2 = readFromJson();
        System.out.println("lista de usuarios agregados:");
        for (Usuario user : ListaDeUsuarios2) {
            System.out.println(user);
        }

        // Actualizar usuarios
        Usuario userToUpdate = ListaDeUsuarios2.get(0);
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
        try (FileReader reader = new FileReader(url)) {
            Type type = new TypeToken<List<Usuario>>() {
            }.getType();
            return json.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static void guardarEnELJson(List<Usuario> users) {
        try (FileWriter writer = new FileWriter(url)) {
            json.toJson(users, writer);
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
        guardarEnELJson(users);
    }

    private static void deleteUser(Usuario userToDelete) {
        List<Usuario> users = readFromJson();
        users.removeIf(user -> user.getId() == userToDelete.getId());
        guardarEnELJson(users);
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