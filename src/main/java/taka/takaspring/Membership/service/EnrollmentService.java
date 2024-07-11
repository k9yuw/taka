package taka.takaspring.Membership.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taka.takaspring.Member.db.UserEntity;
import taka.takaspring.Member.db.UserRepository;
import taka.takaspring.Membership.db.MembershipEntity;
import taka.takaspring.Membership.db.MembershipRepository;
import taka.takaspring.Membership.dto.EnrollmentDto;
import taka.takaspring.Organization.db.OrgEntity;
import taka.takaspring.Organization.db.OrgRepository;

import java.lang.reflect.Member;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final OrgRepository orgRepository;

    @Autowired
    public EnrollmentService(MembershipRepository membershipRepository, UserRepository userRepository, OrgRepository orgRepository){
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
        this.orgRepository = orgRepository;
    }

    public void enrollUserToOrg(EnrollmentDto.EnrollmentRequest request) {

        Long userId = request.getUser().getId();
        Long orgId = request.getOrg().getId();

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
        OrgEntity org = orgRepository.findById(orgId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 단체입니다."));

        MembershipEntity enrollment = MembershipEntity.builder()
                .user(user)
                .org(org)
                .status(MembershipEntity.MembershipStatus.PENDING)
                .build();

        membershipRepository.save(enrollment);
    }

    public EnrollmentDto.EnrollmentResponse approveEnrollment(){

    }

    public EnrollmentDto.EnrollmentResponse rejectEnrollment(){

    }

}
