/* Populate the tables with our data */
create table Dept(
	deptID TEXT PRIMARY KEY NOT NULL UNIQUE CHECK(length(deptID) <= 4),
	name TEXT NOT NULL,
	building TEXT CHECK(building in ("Engineering and Computer Science Building","Science Building","Classroom Building","Undergraduate Classroom Building","Platos Cave"))
	);

create table Student(
	SID INTEGER PRIMARY KEY,
	studentName TEXT NOT NULL,
	major TEXT,
	class TEXT CHECK(class in ("Freshman","Sophomore","Junior","Senior")),
	gpa REAL CHECK(gpa = NULL or gpa >= 0.0 or gpa <= 4.0),
	FOREIGN KEY(major) REFERENCES Dept(deptID)  
);

create table Course(
	courseNum INTEGER CHECK(length(courseNum) = 3),
	deptID TEXT,
	courseName TEXT NOT NULL,
	location TEXT,
	meetDay TEXT CHECK(meetDay in ("MW", "TR", "F")),
	meetTime TIME, --check(meetTime = strftime('%H:%M')), /* HH:MM */
	PRIMARY KEY(courseNum, deptID),
	FOREIGN KEY(deptID) REFERENCES Dept(deptID)
);

create table Enroll(
	courseNum INTEGER,
	deptID	TEXT,
	SID INTEGER,
	FOREIGN KEY(courseNum) REFERENCES Course(courseNum),
	FOREIGN KEY(deptID) REFERENCES Dept(deptID),
	FOREIGN KEY(SID) REFERENCES Student(SID)
);

create table Faculty(
       FID INTEGER NOT NULL PRIMARY KEY,
       Firstname TEXT NOT NULL,
       Middlename TEXT,
       Lastname TEXT,
       officeBuilding TEXT CHECK(officeBuilding in ("Engineering and Computer Science Building","Science Building","Classroom Building","Undergraduate Classroom Building","Platos Cave")),
       officeroom INTEGER,
       Rank TEXT check(Rank in ("Assistant Professor", "Associate Professor", "Professor")),
       Salary INTEGER NOT NULL,
       PrimaryDept TEXT
);

create table Facultyphone(
       FID INTEGER,
       PhoneNumber INTEGER NOT NULL,
       PRIMARY KEY(FID, PhoneNumber),
       FOREIGN KEY(FID) REFERENCES Faculty(FID) 
);

create table Facultyemail(
       FID INTEGER,
       Email TEXT NOT NULL,
       PRIMARY KEY(FID, Email),
       FOREIGN KEY(FID) REFERENCES Faculty(FID)
);

create table Memberof(
       FID INTEGER NOT NULL,
       deptID TEXT NOT NULL CHECK(length(deptID) <= 4),
       ResearchInterest TEXT,
       PRIMARY KEY(FID,deptID),
       FOREIGN KEY(FID) REFERENCES Faculty(FID),
       FOREIGN KEY(deptID) REFERENCES DEPT(deptID)
);

create table Teaches(
       FID INTEGER NOT NULL,       
       courseNum INTEGER,
       deptID TEXT,
       OfficeHours DATETIME, --is there a DATE data type that also looks at day? 
       PRIMARY KEY(deptID,courseNum),
       FOREIGN KEY(courseNum) REFERENCES Course(courseNum),
       FOREIGN KEY(deptID) REFERENCES Course(deptID),
       FOREIGN KEY(FID) REFERENCES Faculty(FID)
);

create table Grants(
       awardNumber INTEGER NOT NULL,
       awardAmount INTEGER NOT NULL,
       amountLeft INTEGER NOT NULL,
       awardTitle TEXT,
       startDate TEXT CHECK(startDate IN ("M","T","W","TH","F")),
       endDate TEXT CHECK(endDate IN ("M","T","W","TH","F")),
       FID INTEGER,
       PRIMARY KEY(awardNumber,FID),
       FOREIGN KEY(FID) REFERENCES Faculty(FID)
);


create table AcademicCoordinator(
       CID INTEGER PRIMARY KEY,
       firstName TEXT NOT NULL,
       middleName TEXT,
       lastName TEXT NOT NULL,
       Salary INTEGER NOT NULL,
       OfficeBuilding  TEXT NOT NULL CHECK(officeBuilding in ("Engineering and Computer Science Building","Science Building","Classroom Building","Undergraduate Classroom Building","Platos Cave")),
       OfficeRoom INTEGER NOT NULL,
       deptID TEXT,
       FOREIGN KEY(deptID) REFERENCES Dept(deptID) 
);

create table ACPhone(
       CID INTEGER,
       Phone INTEGER,
       PRIMARY KEY(CID, Phone),
       FOREIGN KEY(CID) REFERENCES AcademicCoordinator(CID)
);

create table ACEmail(
       CID INTEGER,
       Email TEXT,
       PRIMARY KEY(CID,Email),
       FOREIGN KEY(CID) REFERENCES AcademicCoordinator(CID)
);

create table Advises(
       CID INTEGER NOT NULL,
       StudentID INTEGER PRIMARY KEY,
       FOREIGN KEY(CID) REFERENCES AcademicCoordinator(CID),
       FOREIGN KEY(StudentID) REFERENCES Student(StudentID)
);



insert into Dept values ('CS', 'School of Computer Science', 'Engineering and Computer Science Building');
insert into Dept values ('MATH', 'Department of Mathematics', 'Science Building');
insert into Dept values ('BUS', 'School of Business', 'Classroom Building');
insert into Dept values ('ENG', 'Department of English', 'Undergraduate Classroom Building');


insert into Student values (1282, 'Kelly', 'CS', 'Freshman', 2.5);
insert into Student values (1510, 'Jordan', 'MATH', 'Freshman', 3.0);
insert into Student values (1689, 'Gabriel', 'BUS', 'Senior', 2.4);
insert into Student values (1381, 'Tiffany', 'CS', 'Junior', 4.0);
insert into Student values (1709, 'Cassandra', 'CS', 'Junior', 2.8);
insert into Student values (1101, 'Haley', 'MATH','Senior', 4.0);
insert into Student values (1782, 'Andrew', 'BUS', 'Sophomore',  3.7);
insert into Student values (1468, 'Kris', 'ENG', 'Sophomore', 1.0);
insert into Student values (1641, 'Brittany', 'ENG', 'Senior', 2.7);
insert into Student values (1247, 'Alexis', 'ENG', 'Senior', 3.9);
insert into Student values (1316, 'Austin', 'CS', 'Sophomore', 2.1);
insert into Student values (1911, 'David', 'ENG', 'Senior', 3.2);
insert into Student values (1501, 'Jessica', 'CS', 'Freshman',  3.3);
insert into Student values (1304, 'Jordan', 'MATH','Senior', 2.9);
insert into Student values (1025, 'John', 'ENG', 'Senior', 3.6);
insert into Student values (1934, 'Kyle', 'BUS', 'Junior', 2.1);
insert into Student values (1661, 'Logan', 'CS', 'Freshman',  0.5);

insert into Course values (351, 'CS', 'Intro to Database Systems', 'VELS 19', 'MW', '12:00');
insert into Course values (460, 'CS', 'Operating Systems', 'VMMC 8', 'MW', '14:00');
insert into Course values (122, 'CS', 'OOP', 'VELS 19', 'TR', '12:00');
insert into Course values (453, 'CS', 'Web Data Management', 'VELS 23', 'TR', '09:00');
insert into Course values (122, 'BUS', 'Economics', 'VUCB 30', 'MW', '13:30');
insert into Course values (351, 'BUS', 'Finance', 'VUCB 29', 'TR', '12:00');
insert into Course values (120, 'MATH', 'Algebra', 'VMMC 10', 'MW', '12:00');
insert into Course values (520, 'ENG', 'Intro to Shakespeare', 'VUCB 20', 'TR', '06:00');
insert into Course values (230, 'MATH', 'Linear Algebra', 'VUCB 308', 'TR', '15:00');
insert into Course values (330, 'MATH', 'Trig', 'VECS 113', 'TR', '08:30');
insert into Course values (460, 'MATH', 'Calc III', 'VECS 102', 'TR', '12:30');
insert into Course values (320, 'MATH', 'Discrete Math', 'VUCB 307', 'F', '11:00');
insert into Course values (520, 'CS', 'HPC', 'VECS 307', 'TR', '06:00');

insert into Enroll values (351, 'CS', 1282);
insert into Enroll values (351, 'CS', 1510);
insert into Enroll values (351, 'BUS', 1510);
insert into Enroll values (122, 'BUS', 1510);
insert into Enroll values (460, 'CS', 1316);
insert into Enroll values (460, 'CS', 1689);
insert into Enroll values (520, 'ENG', 1689);
insert into Enroll values (351, 'CS', 1661);
insert into Enroll values (320, 'MATH', 1247);
insert into Enroll values (460, 'CS', 1661);
insert into Enroll values (460, 'MATH', 1661);
insert into Enroll values (351, 'BUS', 1661);
insert into Enroll values (453, 'CS', 1247);
insert into Enroll values (351, 'CS', 1247);
insert into Enroll values (520, 'ENG', 1782);
insert into Enroll values (351, 'BUS', 1025);
insert into Enroll values (520, 'ENG', 1304);
insert into Enroll values (351, 'BUS', 1304);
insert into Enroll values (520, 'CS', 1247);
insert into Enroll values (351, 'CS', 1501);
insert into Enroll values (230, 'MATH', 1101);
insert into Enroll values (120, 'MATH', 1101);
insert into Enroll values (351, 'CS', 1911);
insert into Enroll values (453, 'CS', 1911);
insert into Enroll values (230, 'MATH', 1911);
insert into Enroll values (351, 'CS', 1025);
insert into Enroll values (122, 'BUS', 1316);
insert into Enroll values (520, 'ENG', 1782);
insert into Enroll values (230, 'MATH', 1782);
insert into Enroll values (122, 'BUS', 1282);
insert into Enroll values (520, 'ENG', 1025);
insert into Enroll values (520, 'CS', 1025);
insert into Enroll values (351, 'CS', 1934);
insert into Enroll values (453, 'CS', 1934);
insert into Enroll values (520, 'ENG', 1934);