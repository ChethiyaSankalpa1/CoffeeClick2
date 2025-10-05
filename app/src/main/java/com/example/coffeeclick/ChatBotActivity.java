package com.example.coffeeclick;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatBotActivity extends AppCompatActivity {

    RecyclerView rvChatMessages;
    EditText etMessage;
    Button btnSend, btnBack;
    ChatAdapter chatAdapter;
    List<ChatMessage> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        rvChatMessages = findViewById(R.id.rvChatMessages);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        btnBack = findViewById(R.id.btnBack);

        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList);

        rvChatMessages.setLayoutManager(new LinearLayoutManager(this));
        rvChatMessages.setAdapter(chatAdapter);

        btnBack.setOnClickListener(v -> finish());

        // Welcome message
        addBotMessage("Hello! I'm your Coffee Assistant. Ask me anything about coffee!");

        btnSend.setOnClickListener(v -> {
            String userMessage = etMessage.getText().toString().trim();
            if (!userMessage.isEmpty()) {
                addUserMessage(userMessage);
                etMessage.setText("");
                
                // Generate bot response
                String botResponse = generateResponse(userMessage);
                addBotMessage(botResponse);
            }
        });
    }

    private void addUserMessage(String message) {
        messageList.add(new ChatMessage(message, true));
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        rvChatMessages.smoothScrollToPosition(messageList.size() - 1);
    }

    private void addBotMessage(String message) {
        messageList.add(new ChatMessage(message, false));
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        rvChatMessages.smoothScrollToPosition(messageList.size() - 1);
    }

    private String generateResponse(String userMessage) {
        String message = userMessage.toLowerCase();

        // Coffee recommendations
        if (message.contains("recommend") || message.contains("suggest")) {
            return "I recommend our Espresso for a strong kick, Cappuccino for a creamy taste, or Latte for something smooth and mild!";
        }
        
        // Espresso questions
        if (message.contains("espresso")) {
            return "Espresso is a strong, concentrated coffee shot. It's perfect for a quick energy boost! Price: LKR 350. Would you like to order?";
        }
        
        // Cappuccino questions
        if (message.contains("cappuccino")) {
            return "Cappuccino is made with espresso and steamed milk foam. It's creamy and delicious! Price: LKR 450. Shall I add it to your cart?";
        }
        
        // Latte questions
        if (message.contains("latte")) {
            return "Latte is a smooth coffee with lots of steamed milk. Perfect for a relaxing moment! Price: LKR 400. Want to try it?";
        }
        
        // Price questions
        if (message.contains("price") || message.contains("cost") || message.contains("how much")) {
            return "Our prices are:\n• Espresso - LKR 350\n• Cappuccino - LKR 450\n• Latte - LKR 400\n\nAll freshly brewed!";
        }
        
        // Menu questions
        if (message.contains("menu") || message.contains("what") || message.contains("have")) {
            return "We have:\n☕ Espresso - Strong coffee shot (LKR 350)\n☕ Cappuccino - With milk foam (LKR 450)\n☕ Latte - Smooth milk coffee (LKR 400)\n\nWhich one interests you?";
        }
        
        // Order questions
        if (message.contains("order") || message.contains("buy")) {
            return "To place an order:\n1. Browse our menu (Explore tab)\n2. Select quantity with + button\n3. Click 'Add to Cart'\n4. Go to Cart and checkout!\n\nNeed help with anything specific?";
        }
        
        // Delivery questions
        if (message.contains("delivery") || message.contains("deliver")) {
            return "We offer fast delivery! After placing your order, we'll deliver it to you within 30-45 minutes. Delivery is free for orders above LKR 1000!";
        }
        
        // Hours questions
        if (message.contains("hours") || message.contains("open") || message.contains("time")) {
            return "We're open 7 days a week!\n⏰ Mon-Fri: 7:00 AM - 10:00 PM\n⏰ Sat-Sun: 8:00 AM - 11:00 PM\n\nOrder anytime through the app!";
        }
        
        // Strong coffee
        if (message.contains("strong") || message.contains("energy") || message.contains("wake")) {
            return "For a strong coffee, I recommend Espresso! It's a concentrated shot that will definitely wake you up. Double shot available too!";
        }
        
        // Mild coffee
        if (message.contains("mild") || message.contains("light") || message.contains("smooth")) {
            return "For something mild and smooth, try our Latte! It has more milk and less coffee intensity. Perfect for a relaxing drink!";
        }
        
        // Help questions
        if (message.contains("help") || message.contains("how")) {
            return "I can help you with:\n• Coffee recommendations\n• Menu and prices\n• How to place orders\n• Delivery information\n• Store hours\n\nWhat would you like to know?";
        }
        
        // Greetings
        if (message.contains("hello") || message.contains("hi") || message.contains("hey")) {
            return "Hello! Welcome to CoffeeClick! ☕ How can I help you today? Would you like to see our menu or get a recommendation?";
        }
        
        // Thanks
        if (message.contains("thank") || message.contains("thanks")) {
            return "You're welcome! Enjoy your coffee! ☕ Feel free to ask if you need anything else!";
        }
        
        // Default response
        return "I'm here to help with coffee questions! You can ask me about:\n• Our menu and prices\n• Coffee recommendations\n• How to order\n• Delivery info\n\nWhat would you like to know?";
    }
}
