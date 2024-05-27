package vn.aptech.demo.seeders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import vn.aptech.demo.models.ESettingType;
import vn.aptech.demo.models.Gallery;
import vn.aptech.demo.models.Group;
import vn.aptech.demo.models.GroupMember;
import vn.aptech.demo.models.Post;
import vn.aptech.demo.models.RelationshipUsers;
import vn.aptech.demo.models.ReportTypes;
import vn.aptech.demo.models.Requirement;
import vn.aptech.demo.models.Role;
import vn.aptech.demo.models.User;
import vn.aptech.demo.models.UserSetting;
import vn.aptech.demo.repository.GalleryRepository;
import vn.aptech.demo.repository.GroupMemberRepository;
import vn.aptech.demo.repository.GroupRepository;
import vn.aptech.demo.repository.PostRepository;
import vn.aptech.demo.repository.RelationshipUsersRepository;
import vn.aptech.demo.repository.ReportTypeRepository;
import vn.aptech.demo.repository.RequirementRepository;
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
	private RequirementRepository requirementRep;
	private GalleryRepository galleryRep;
	public DatabaseSeeder(
			UserRepository urep,
			GroupRepository grep,
			RoleRepository roleRep,
			GroupMemberRepository memRep,
			PasswordEncoder passwordEncoder,
			RelationshipUsersRepository relationshipsRep,
			ReportTypeRepository reportTypeRep,
			PostRepository postRep,
			RequirementRepository requirementRep,
			GalleryRepository galleryRep
			) {
		this.groupRep = grep;
		this.userRep = urep;
		this.memRep= memRep;
		this.roleRep= roleRep;
		this.passwordEncoder = passwordEncoder;
		this.relationshipsRep = relationshipsRep;
		this.reportTypeRep = reportTypeRep;
		this.postRep = postRep;
		this.requirementRep = requirementRep;
		this.galleryRep = galleryRep;
	}
	@EventListener
	public void seed(ContextRefreshedEvent event) {
        seederRole();
        seederUser();
        seederGroup();
        seederGroupMember();
        seederRelationshipUser();
        seederReportType();
        seederPosts();
        seederRequirements();

    }
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
	        for(int i =1; i<=10;i++) {
	        	String gernateGen= i%2==0?"female":"male";
	        	String phone = "09192343"+ (int) Math.floor(Math.random()*70+10);
	        	logger.info(phone);
	        	User user= new User("user"+i, "user"+i+"@gmail.com",passwordEncoder.encode("a123456"), "User "+i,gernateGen, phone, LocalDateTime.now(), LocalDateTime.now());
	        	HashSet<Role> user1roles = new HashSet<Role>();
	        	user1roles.add(userRole);
	        	user.setRoles(user1roles);
	        	user.set_active(true);
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
			for(int i =1; i<=7;i++) {
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
					int numberF = ran1==users.size()?ran1-1:ran1;
					List<Integer> listRan = new LinkedList<Integer>();
					listRan.add(ran1);
					for(int i = 0; i<= numberF; i++) {
						GroupMember member = new GroupMember();
						member.setGroup(group);
						int ran2 = (int) Math.round(Math.random()*users.size());
						ran2 = ran2>=0 && ran2<5 && !(ran1==3&&ran2==1)? ran2: ran2-1;
						logger.info("Random 1: "+ran1);						
						do {
							if(!listRan.contains(ran2)) {
								User userMem = users.get(ran2);
								member.setUser(userMem);
								listRan.add(ran2);
//								logger.info("Group member "+ userMem.getUsername());
							}else {
								ran2 = (int) Math.round(Math.random()*users.size());
								ran2 = ran1==3&&ran2==1?  (int) Math.round(Math.random()*users.size()) : ran2;
							}
							logger.info("Random 2: "+ran2);
						}while(listRan.contains(ran2));
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
			List<User> users = userRep.findAll();
			List<RelationshipUsers> createRelationship = Arrays.asList(
					//Friends
					new RelationshipUsers(users.get(0), users.get(1), ERelationshipType.FRIEND),
					new RelationshipUsers(users.get(0), users.get(3), ERelationshipType.FRIEND),
					new RelationshipUsers(users.get(1), users.get(3), ERelationshipType.FRIEND),
					new RelationshipUsers(users.get(3), users.get(2), ERelationshipType.FRIEND),
					new RelationshipUsers(users.get(4), users.get(9), ERelationshipType.FRIEND),
					new RelationshipUsers(users.get(5), users.get(2), ERelationshipType.FRIEND),
					new RelationshipUsers(users.get(6), users.get(4), ERelationshipType.FRIEND),
					new RelationshipUsers(users.get(7), users.get(3), ERelationshipType.FRIEND),
					//Blocked
					new RelationshipUsers(users.get(1), users.get(2), ERelationshipType.BLOCKED),
					new RelationshipUsers(users.get(2), users.get(0), ERelationshipType.BLOCKED),
					new RelationshipUsers(users.get(0), users.get(8), ERelationshipType.BLOCKED),
					new RelationshipUsers(users.get(0), users.get(7), ERelationshipType.BLOCKED)
					); 
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
					new Post("THis is a post",users.get(0) , LocalDateTime.now().minusDays(1),ESettingType.PUBLIC),
					new Post("Hellow world ",users.get(0) , LocalDateTime.now(),ESettingType.FOR_FRIEND),
					new Post("Tomcat started on port 9001",users.get(1) , LocalDateTime.now().minusDays(2),ESettingType.HIDDEN),
					new Post("Hello world",users.get(1) , LocalDateTime.now(),ESettingType.FOR_FRIEND),
					new Post("HHAHAHAHHAHHAH",users.get(2) , LocalDateTime.now().minusDays(3),ESettingType.PUBLIC),
					new Post("Welcome to my WOLRD",users.get(2) , LocalDateTime.now().minusDays(1),ESettingType.PUBLIC),
					new Post("HELLOW ",users.get(3) , LocalDateTime.now().minusDays(1),ESettingType.FOR_FRIEND),
					new Post("Let add some word",users.get(3) , LocalDateTime.now().minusHours(3),ESettingType.PUBLIC),
					new Post("I try to add some word",users.get(4) , LocalDateTime.now(),ESettingType.HIDDEN),
					new Post("I dont know what to post",users.get(4) , LocalDateTime.now(),ESettingType.FOR_FRIEND),
					new Post("I love you",users.get(4) , LocalDateTime.now(),ESettingType.PUBLIC),
					new Post("I will go to the cinema",users.get(5) , LocalDateTime.now(),ESettingType.FOR_FRIEND),
					new Post("Deadline like the death in line",users.get(5) , LocalDateTime.now(),ESettingType.HIDDEN),
					new Post("Just seeding some posts",users.get(6) , LocalDateTime.now(),ESettingType.FOR_FRIEND),
					new Post("Trying post",users.get(6) , LocalDateTime.now(),ESettingType.PUBLIC),
					new Post("Where's my car?",users.get(7) , LocalDateTime.now(),ESettingType.PUBLIC),
					new Post("I'm broke",users.get(7) , LocalDateTime.now(),ESettingType.PUBLIC),
					new Post("Nothing to add ",users.get(8) , LocalDateTime.now(),ESettingType.PUBLIC),
					new Post("Can I finish my dealine?",users.get(8) , LocalDateTime.now(),ESettingType.PUBLIC),
					new Post("HELP ME!!",users.get(9) , LocalDateTime.now(),ESettingType.PUBLIC)
					);
			for (Iterator<Post> iterator = listPo.iterator(); iterator.hasNext();) {
				Post post = (Post) iterator.next();
				post.setColor("inherit") ;
				post.setBackground_color("inherit");
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
					Post postFromMem = new Post("Seeding a post in group "+group.getGroupname()+" from user "+member.getUsername(),member, LocalDateTime.now().minusDays(randomDay),group);
					postFromMem.setColor("inherit") ;
					postFromMem.setBackground_color("inherit");
					postRep.save(postFromMem);
					logger.info("Saved Post to Group "+group.getGroupname());
				}
			}

			List<Post> listposts = Arrays.asList(
					new Post("Post with image", users.get(0), LocalDateTime.now(), ESettingType.PUBLIC),
					new Post("My Image", users.get(1), LocalDateTime.now(), ESettingType.FOR_FRIEND)
					);
			for (Iterator<Post> iterator = listposts.iterator(); iterator.hasNext();) {
				Post post = (Post) iterator.next();
				postRep.save(post);
			}
			//pic_rogue_2_1
			List<Gallery> galleries = Arrays.asList(
						new Gallery(users.get(0), postRep.findById((long) postRep.count()-1).get(), "pic1.png", "image"),
						new Gallery(users.get(1), postRep.findById((long) postRep.count()).get(), "pic2.png", "image")
					);
			for (Iterator<Gallery> iterator = galleries.iterator(); iterator.hasNext();) {
				Gallery gallery = (Gallery) iterator.next();
				gallery.setCreate_at(LocalDateTime.now());
				galleryRep.save(gallery);
			}
		}else {
			logger.trace("Seeding Post is not required");
		}
	}
	private void seederRequirements() {
		List<Requirement> listre = requirementRep.findAll();
		if(listre.isEmpty()) {
			List<User> users = userRep.findAll();
			List<Group> groups = groupRep.findAll();
			if(!users.isEmpty() && !groups.isEmpty()) {
				List<Requirement> list = Arrays.asList(
						//Add friends requirements
						new Requirement(users.get(0),users.get(4)),
						new Requirement(users.get(0),users.get(5)),
						new Requirement(users.get(9),users.get(0)),
						new Requirement(users.get(6), users.get(0))
						//Join group
						
						);			
				for (Iterator<Requirement> iterator = list.iterator(); iterator.hasNext();) {
					Requirement requirement = (Requirement) iterator.next();
					requirementRep.save(requirement);
					logger.info("Requirement "+requirement.getSender().getUsername()+" "+requirement.getType()+" saved");
				}
			}
		}else{
			logger.trace("Requirement is not required seeder");
		}
	
	}
}
