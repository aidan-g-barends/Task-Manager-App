package ac.za.mycput.taskmanager.Controller;

public record LoginResponse(String token, Long id, String name, String email, String role) {
}