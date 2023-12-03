package com.boney.passkey.service;

import com.boney.passkey.cryptography.ZeroKnowledgeProofManager;
import com.boney.passkey.model.User;
import com.boney.passkey.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ZeroKnowledgeProofManager zkProofManager;

    public void registerUser(String userId, String passkey) {
        User user = new User();
        user.setUserId(userId);
        user.setPasskey(passkey);
        userRepository.saveUser(user);
    }

    public boolean verifyPasskey(String userId, String passkey) {
        User user = userRepository.getUserById(userId);
        if (user != null) {
            // Implement passkey verification logic
            return zkProofManager.performZeroKnowledgeProof(userId, passkey, user.getPasskey());
        }
        return false;
    }

}
