package com.pi.clique_vagas_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;

import org.springframework.boot.test.context.SpringBootTest;

import com.pi.clique_vagas_api.model.jobPost.JobPostingModel;
import com.pi.clique_vagas_api.model.jobPost.LikeJobPostingModel;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.model.users.typeUsers.CompanyModel;
import com.pi.clique_vagas_api.repository.jobPosting.JobPostingRepository;
import com.pi.clique_vagas_api.repository.jobPosting.LikeRepository;
import com.pi.clique_vagas_api.repository.users.CompanyRepository;
import com.pi.clique_vagas_api.repository.users.UserRepository;
import com.pi.clique_vagas_api.resources.enums.Status;
import com.pi.clique_vagas_api.resources.enums.UserRole;
import com.pi.clique_vagas_api.service.jobPost.JobPostingService;

import jakarta.transaction.Transactional;

@SpringBootTest
public class JobPostingRepositoryTest {

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private JobPostingService jobPostingService;

    @Autowired
    private LikeRepository likeJobPostingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    @Transactional
    public void testDeleteJobPostingWithLikes() {

        UserModel user = new UserModel();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johwddwn.doe@example.com");
        user.setPhone("123456789");
        user.setPassword("password");
        user.setRole(UserRole.COMPANY);
        user = userRepository.save(user);

        CompanyModel company = new CompanyModel();
        company.setUserId(user);
        company.setCompanyName("Example Company");
        company.setCnpj("12.345.678/0001-99");
        company.setTelephoneResponsible("987654321");
        company.setSectorOfOperation("Technology");
        company.setWebsiteLink("http://example.com");
        company = companyRepository.save(company);

        company = companyRepository.save(company);

        JobPostingModel jobPosting = new JobPostingModel();
        jobPosting.setTitle("Test Job");
        jobPosting.setDescription("Test Description");
        jobPosting.setJobPostingStatus(Status.ACTIVE);
        jobPosting.setAddress("TESTE");
        jobPosting.setApplicationDeadline(ZonedDateTime.now());
        jobPosting.setCompany(company);

        jobPosting = jobPostingRepository.save(jobPosting);

        LikeJobPostingModel like = new LikeJobPostingModel();
        like.setJobPosting(jobPosting);
        like.setUser(user);

        like = likeJobPostingRepository.save(like);

        jobPostingService.delete(jobPosting.getId(), company);

        assertThat(likeJobPostingRepository.findById(like.getId())).isEmpty();
    }
}
