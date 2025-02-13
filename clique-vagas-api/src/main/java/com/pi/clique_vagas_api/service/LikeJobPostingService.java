package com.pi.clique_vagas_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.jobPost.JobPostingModel;
import com.pi.clique_vagas_api.model.jobPost.LikeJobPostingModel;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.repository.jobPosting.LikeRepository;

@Service
public class LikeJobPostingService {

    @Autowired
    private LikeRepository likeRepository;

    public LikeJobPostingModel likePost(JobPostingModel jobPosting, UserModel user) {

        if (likeRepository.findByJobPostingAndUser(jobPosting, user).isPresent()) {
            throw new EventNotFoundException("User already liked this post");
        }

        LikeJobPostingModel like = new LikeJobPostingModel();
        like.setJobPosting(jobPosting);
        like.setUser(user);

        return likeRepository.save(like);
    }

    public void unlikePost(JobPostingModel jobPosting, UserModel user) {
        LikeJobPostingModel like = likeRepository.findByJobPostingAndUser(jobPosting, user)
                .orElseThrow(() -> new EventNotFoundException("Like not found"));
        likeRepository.delete(like);
    }

    public Long getLikeCount(JobPostingModel jobPosting) {
        return likeRepository.countByJobPosting(jobPosting);
    }

    public boolean hasUserLikedPost(JobPostingModel jobPosting, UserModel user) {
        return likeRepository.existsByJobPostingAndUser(jobPosting, user);
    }

}
