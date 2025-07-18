package com.project.complaint_tracker.service;

import com.project.complaint_tracker.entity.Complaint;
import com.project.complaint_tracker.entity.User;
import com.project.complaint_tracker.repository.ComplaintRepository;
import com.project.complaint_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComplaintRepository complaintRepository;


    public Complaint submit(Complaint complaint, String name) {
        Optional<User> user = userRepository.findByUsername(name);
        user.ifPresent(complaint::setUser);
        return complaintRepository.save(complaint);
    }

    public List<Complaint> getMyComplaints(String name) {
        User user = userRepository.findByUsername(name).orElseThrow();
        return complaintRepository.findByUserId(user.getId());
    }

    public Complaint updateStatus(Long id, String status) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow();
        complaint.setStatus(status);
        return complaintRepository.save(complaint);
    }
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }


}
