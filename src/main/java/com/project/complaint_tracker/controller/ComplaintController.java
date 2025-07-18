package com.project.complaint_tracker.controller;

import com.project.complaint_tracker.entity.Complaint;
import com.project.complaint_tracker.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.ReactiveOffsetScrollPositionHandlerMethodArgumentResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping("/submit")
    public ResponseEntity<Complaint> submit(@RequestBody Complaint complaint, Principal principal){
        Complaint saved = complaintService.submit(complaint,principal.getName());
        return ResponseEntity.ok(saved);

    }

    @GetMapping("/my")
    public ResponseEntity<List<Complaint>> getMyComplaints(Principal principal){
        List<Complaint> complaints= complaintService.getMyComplaints(principal.getName());
        return ResponseEntity.ok(complaints);

    }

    // 📋 View all complaints (ADMIN only)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Complaint> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(complaintService.updateStatus(id, status));
    }
}
