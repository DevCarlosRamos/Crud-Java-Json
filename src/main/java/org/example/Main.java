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

    private static final String url = "/home/carlos/Escritorio/carpeta/Crud-Java-Json/src/main/resources/data.json";
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
        List<Usuario> ListaDeUsuarios2 = LeerJson();
        System.out.println("lista de usuarios agregados:");
        for (Usuario user : ListaDeUsuarios2) {
            System.out.println(user);
        }

        // Actualizar usuarios
        Usuario usuarioActualizado = ListaDeUsuarios2.get(2);
        usuarioActualizado.setNombre("Nandito");
        actualizarUsuario(usuarioActualizado);

        // Read updated data from JSON file
        List<Usuario> updatedUsers = LeerJson();
        System.out.println("Updated users from JSON:");
        for (Usuario user : updatedUsers) {
            System.out.println(user);
        }

        // Delete user
        deleteUser(updatedUsers.get(0));

        // Read final data from JSON file
        List<Usuario> finalUsers = LeerJson();
        System.out.println("Final users from JSON:");
        for (Usuario user : finalUsers) {
            System.out.println(user);
        }
    }

    private static List<Usuario> LeerJson() {
        try (FileReader reader = new FileReader(url)) {
            Type type = new TypeToken<List<Usuario>>() {
            }.getType();
            return json.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private static void guardarEnELJson(List<Usuario> ListaDeUsuarios) {
        try (FileWriter writer = new FileWriter(url)) {
            json.toJson(ListaDeUsuarios, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void actualizarUsuario(Usuario userToUpdate) {
        List<Usuario> users = LeerJson();
        for (Usuario user : users) {
            if (user.getId() == userToUpdate.getId()) {
                user.setNombre(userToUpdate.getNombre());
                break;
            }
        }
        guardarEnELJson(users);
    }

    private static void deleteUser(Usuario userToDelete) {
        List<Usuario> users = LeerJson();
        users.removeIf(user -> user.getId() == userToDelete.getId());
        guardarEnELJson(users);
    }

    static class Usuario {
        private int id;
        private String nombre;

        public Usuario(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return "Usuario{" +
                    "id=" + id +
                    ", Nombre='" + nombre + '\'' +
                    '}';
        }
    }
}