package vn.aptech.demo.seeders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import vn.aptech.demo.models.EGroupRole;
import vn.aptech.demo.models.ERelationshipType;
import vn.aptech.demo.models.ERole;
import vn.aptech.demo.models.Group;
import vn.aptech.demo.models.GroupMember;
import vn.aptech.demo.models.Post;
import vn.aptech.demo.models.RelationshipUsers;
import vn.aptech.demo.models.ReportTypes;
import vn.aptech.demo.models.Role;
import vn.aptech.demo.models.User;
import vn.aptech.demo.repository.GroupMemberRepository;
import vn.aptech.demo.repository.GroupRepository;
import vn.aptech.demo.repository.PostRepository;
import vn.aptech.demo.repository.RelationshipUsersRepository;
import vn.aptech.demo.repository.ReportTypeRepository;
import vn.aptech.demo.repository.RoleRepository;
import vn.aptech.demo.repository.UserRepository;


@Component
public class DatabaseSeeder {
	private Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);
	private UserRepository userRep;
	private GroupRepository groupRep;
	private RoleRepository roleRep;
	private PasswordEncoder passwordEncoder;
	private GroupMemberRepository memRep;
	private RelationshipUsersRepository relationshipsRep;
	private ReportTypeRepository reportTypeRep;
	private PostRepository postRep;
	public DatabaseSeeder(
			UserRepository urep,
			GroupRepository grep,
			RoleRepository roleRep,
			GroupMemberRepository memRep,
			PasswordEncoder passwordEncoder,
			RelationshipUsersRepository relationshipsRep,
			ReportTypeRepository reportTypeRep,
			PostRepository postRep
			) {
		this.groupRep = grep;
		this.userRep = urep;
		this.memRep= memRep;
		this.roleRep= roleRep;
		this.passwordEncoder = passwordEncoder;
		this.relationshipsRep = relationshipsRep;
		this.reportTypeRep = reportTypeRep;
		this.postRep = postRep;
	}
//	@EventListener
//	public void seed(ContextRefreshedEvent event) {
//        seederRole();
//        seederUser();
//        seederGroup();
//        seederGroupMember();
//        seederRelationshipUser();
//        seederReportType();
//        seederPosts();
//    }
	private void seederRole() {
		 List<Role> roles = roleRep.findAll();
		 if(roles.isEmpty()) {
			Role admin = new Role();
	        admin.setName(ERole.ROLE_ADMIN);
	        roleRep.save(admin);	
	        Role user = new Role();
	        user.setName(ERole.ROLE_USER);
	        roleRep.save(user);
	        logger.info("Roles saved");
		 }else {
			 logger.trace("Seeding User Role is not required");
		 }	 
	}
	private void seederUser() {
		List<User> users = userRep.findAll();
		if(users.isEmpty()) {
			Role userRole = roleRep.findByName(ERole.ROLE_USER).get();
	        for(int i =0; i<5;i++) {
	        	String gernateGen= i%2==0?"female":"name";
	        	User user= new User("user"+i, "user"+i+"@gmail.com",passwordEncoder.encode("a123456"), "User "+i,gernateGen, "091923453"+i, LocalDateTime.now(), LocalDateTime.now());
	        	HashSet<Role> user1roles = new HashSet<Role>();
	        	user1roles.add(userRole);
	        	user.setRoles(user1roles);
	        	logger.info(user.toString());
	        	userRep.save(user);
	        	logger.info("User "+i+" saved");
	        }
		}else {
			logger.trace("Seeding User is not required");
		}
	}
	private void seederGroup() {
		List<Group> groups = groupRep.findAll();
		if(groups.isEmpty()) {
			for(int i =0; i<5;i++) {
	        	Group group= new Group("Group "+i,"Description of Group "+i);
	        	group.setActive(true);
	        	group.setPublic_group(i%2==0);
	        	groupRep.save(group);
	        	logger.info("Group "+i+" saved");
	        }
		}else {
			logger.trace("Seeding Group is not required");
		}
	}
	private void seederGroupMember() {
		List<GroupMember> groupmembers= memRep.findAll();
		if(groupmembers.isEmpty()) {
			List<User> users = userRep.findAll();
			List<Group> groups = groupRep.findAll();
			if(!users.isEmpty()&& !groups.isEmpty()) {
				for (Iterator<Group> iterator = groups.iterator(); iterator.hasNext();) {
					Group group = (Group) iterator.next();
					GroupMember creator = new GroupMember();
					int ran1 = (int) Math.round(Math.random()*users.size()) ;
					ran1 = ran1>= 0&& ran1 <5?ran1:ran1-1;
//					logger.info("Random 1: "+ran1);
					User user = users.get(ran1);
					creator.setGroup(group);
					creator.setUser(user);
					creator.setRole(EGroupRole.GROUP_CREATOR);
					memRep.save(creator);
					logger.info("Group "+group.getId()+" creator userid: "+user.getUsername()+" saved");
					for(int i = 0; i<= ran1; i++) {
						GroupMember member = new GroupMember();
						member.setGroup(group);
						int ran2 = (int) Math.round(Math.random()*users.size());
						ran2 = ran2>=0 && ran2<5? ran2: ran2-1;
//						logger.info("Random 2: "+ran2);
						do {
							if(ran2!=ran1) {
								User userMem = users.get(ran2);
								member.setUser(userMem);			
//								logger.info("Group member "+ userMem.getUsername());
							}else {
								ran2 = (int) Math.round(Math.random()*users.size());
							}
						}while(ran2==ran1);
						if(member.getUser()!=null) {
							member.setRole(EGroupRole.MEMBER);
							memRep.save(member);				
							logger.info("Group "+group.getId()+" member saved");
						}
					}
				}
			}
		}else {
			logger.trace("Seeding Group member is not required");
		}
	}

	private void seederRelationshipUser() {
		List<RelationshipUsers> relas = relationshipsRep.findAll();
		if(relas.isEmpty()) {
			List<RelationshipUsers> createRelationship = new LinkedList<RelationshipUsers>(); 
			List<User> users = userRep.findAll();
			//Friends
				//User0
				RelationshipUsers relationship1 = new RelationshipUsers(users.get(0), users.get(1), ERelationshipType.FRIEND);
				createRelationship.add(relationship1);
				RelationshipUsers relationship2 = new RelationshipUsers(users.get(0), users.get(3), ERelationshipType.FRIEND);
				createRelationship.add(relationship2);
				//User1
				RelationshipUsers relationship3 = new RelationshipUsers(users.get(1), users.get(3), ERelationshipType.FRIEND);
				createRelationship.add(relationship3);
				//User3
				RelationshipUsers relationship4 = new RelationshipUsers(users.get(3), users.get(2), ERelationshipType.FRIEND);
				createRelationship.add(relationship4);
			//Blocked
				RelationshipUsers relationship5 = new RelationshipUsers(users.get(1), users.get(2), ERelationshipType.BLOCKED);
				createRelationship.add(relationship5);
				RelationshipUsers relationship6 = new RelationshipUsers(users.get(2), users.get(0), ERelationshipType.BLOCKED);
				createRelationship.add(relationship6);
			for (RelationshipUsers rel : createRelationship) {
				relationshipsRep.save(rel);
				logger.info("Relationship [ "+rel.getUser1().getUsername()+", "+rel.getUser2().getUsername()+" ] "+rel.getType()+" saved");
			}
		}else {
			logger.trace("Seeding User Relationship is not required");
		}
	}
	private void seederReportType() {
		List<ReportTypes> reportTypes = reportTypeRep.findAll();
		if(reportTypes.isEmpty()) {
			List<ReportTypes>listRep = Arrays.asList(
                    new ReportTypes("nudity","If someone is in immediate danger, get help before reporting to Beehub. Don't wait."), 
                    new ReportTypes("violence","If someone is in immediate danger, get help before reporting to Facebook. Don't wait."),
                    new ReportTypes("spam","We don't allow things such as: Buying, selling or giving away accounts, roles or permissions, directing people away from Facebook through the misleading use of links"),
                    new ReportTypes("involve a child","If someone is in immediate danger, get help before reporting to Facebook. Don't wait."),
                    new  ReportTypes("drugs","If someone is in immediate danger, get help before reporting to Facebook. Don't wait.")
			);
			for (Iterator<ReportTypes> iterator = listRep.iterator(); iterator.hasNext();) {
				ReportTypes reportT = (ReportTypes) iterator.next();
				reportTypeRep.save(reportT);
				logger.info("Saved Report Type "+reportT.getTitle());
			}
		}else {
			logger.trace("Seeding Report Type is not required");
		}
	}
	private void seederPosts() {
		List<Post> posts = postRep.findAll();
		if(posts.isEmpty()) {
			List<User> users = userRep.findAll();
			List<Post> listPo = Arrays.asList(
					new Post("Demo 1 Post of User 0",users.get(0) , LocalDateTime.now().minusDays(1)),
					new Post("Demo 2 Post of User 0 ",users.get(0) , LocalDateTime.now()),
					new Post("Demo 3 Post of User 1",users.get(1) , LocalDateTime.now().minusDays(2)),
					new Post("Demo 4 Post of User 1",users.get(1) , LocalDateTime.now()),
					new Post("Demo 5 Post of User 2",users.get(2) , LocalDateTime.now().minusDays(3)),
					new Post("Demo 6 Post of User 2",users.get(2) , LocalDateTime.now().minusDays(1)),
					new Post("Demo 7 Post of User 3",users.get(3) , LocalDateTime.now().minusDays(1)),
					new Post("Demo 8 Post of User 3",users.get(3) , LocalDateTime.now().minusHours(3)),
					new Post("Demo 9 Post of User 4",users.get(4) , LocalDateTime.now()),
					new Post("Demo 10 Post of User 4",users.get(4) , LocalDateTime.now())
					);
			for (Iterator<Post> iterator = listPo.iterator(); iterator.hasNext();) {
				Post post = (Post) iterator.next();
				postRep.save(post);
				logger.info("Saved Post.");
			}
			List<Group> groups = groupRep.findAll();
			for (Iterator<Group> iterator = groups.iterator(); iterator.hasNext();) {
				Group group = (Group) iterator.next();
				List<GroupMember> members = group.getGroup_members();
				for (Iterator<GroupMember> iterator2 = members.iterator(); iterator2.hasNext();) {
					GroupMember groupMember = (GroupMember) iterator2.next();
					User member = groupMember.getUser();
					int randomDay=(int) Math.round(Math.random()+5);
					Post postFromMem = new Post("Demo post in group "+group.getGroupname()+" from user "+member.getUsername(),member, LocalDateTime.now().minusDays(randomDay));
					postFromMem.setGroup(group);
					postRep.save(postFromMem);
					logger.info("Saved Post to Group "+group.getGroupname());
				}
			}
		}else {
			logger.trace("Seeding Post is not required");
		}
	}
}
