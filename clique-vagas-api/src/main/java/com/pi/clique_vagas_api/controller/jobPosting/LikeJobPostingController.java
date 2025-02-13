package com.pi.clique_vagas_api.controller.jobPosting;

import com.pi.clique_vagas_api.model.jobPost.JobPostingModel;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.service.JobPostingService;
import com.pi.clique_vagas_api.service.LikeJobPostingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeJobPostingController {

    @Autowired
    private LikeJobPostingService likeService;

    @Autowired
    private JobPostingService jobPostingService;

    @PostMapping("/{jobPostingId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long jobPostingId, @AuthenticationPrincipal UserModel user) {
        JobPostingModel jobPosting = jobPostingService.findById(jobPostingId);
        likeService.likePost(jobPosting, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{jobPostingId}/unlike")
    public ResponseEntity<?> unlikePost(@PathVariable Long jobPostingId, @AuthenticationPrincipal UserModel user) {
        JobPostingModel jobPosting = jobPostingService.findById(jobPostingId);
        likeService.unlikePost(jobPosting, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{jobPostingId}/count")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long jobPostingId) {
        JobPostingModel jobPosting = jobPostingService.findById(jobPostingId);
        Long likeCount = likeService.getLikeCount(jobPosting);
        return ResponseEntity.ok(likeCount);
    }

    @GetMapping("/{jobPostingId}/check")
    public ResponseEntity<Boolean> checkIfUserLikedPost(@PathVariable Long jobPostingId,
            @AuthenticationPrincipal UserModel user) {
        JobPostingModel jobPosting = jobPostingService.findById(jobPostingId);
        boolean hasLiked = likeService.hasUserLikedPost(jobPosting, user);
        return ResponseEntity.ok(hasLiked);
    }
}