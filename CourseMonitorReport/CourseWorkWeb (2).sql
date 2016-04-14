create database CourseMonitorReport
use CourseMonitorReport

create table Course(
	CourseId varchar(50) primary key,
	CourseName varchar(50),
	AcademicSession varchar(50),
	CourseImg varchar(100),
	Description varchar(200),
)

SELECT * FROM Course 

insert into Course values ('C1','Interaction design','Summer','interactiondesign.jpg','Good')
insert into Course values ('C2','Java','Summer','java.png','Excellent')
insert into Course values ('C3','Database','Summer','database.jpg','Bad')
insert into Course values ('C4','Web design','Summer','webdesign.jpg','Normal')

update Course
set CourseImg = 'dotnet.png'
where CourseId = 'C6'


create table Staff(
	ST_Id integer primary key,
	ST_Name varchar(50),
	ST_Email varchar(50),
	ST_Username varchar(50),
	ST_Password varchar(50)
)

insert into Staff values (1,'Nghia','nghia@gmail.com','kid','123')
insert into Staff values (2,'Nam','nam@gmail.com','nam','123')
insert into Staff values (3,'Dung','dung@gmail.com','dung','123')
insert into Staff values (4,'Mike','mike@gmail.com','mike','123')
insert into Staff values (5,'John','john@gmail.com','john','123')
insert into Staff values (6,'Vy','vy@gmail.com','vy','123')

SELECT * FROM Staff where ST_Name = 'Nam'

create  table AssignCourse
(
 idAC integer,
CourseId varchar(50) foreign key references Course(CourseId),
StudentCount integer,
Status integer,
Year integer,
CL_Id integer foreign key references Staff(ST_Id),
CM_Id integer foreign key references Staff(ST_Id),
primary key (CourseId,Year),
)
drop table AssignCourse

INSERT INTO AssignCourse VALUES(1,'C1',100,0,2015,1,2)
INSERT INTO AssignCourse VALUES(2,'C1',50,0,2016,2,3)
INSERT INTO AssignCourse VALUES(3,'C4',100,0,2015,2,1)

SELECT * FROM AssignCourse where idAc = 3

create table PVC(
	PVC_Id integer primary key,
	PVC_Name varchar(50),
	PVC_Email varchar(50)
)
Insert into PVC Values (1,'Nam','namhhgc00529@fpt.edu.vn')
Insert into PVC Values (2,'Nghia','nghia@fpt.edu.vn')
Insert into PVC Values (3,'Dung','dung@fpt.edu.vn')
SELECT * FROM PVC 

create table DLT(
	DLT_Id integer primary key,
	DLT_Name varchar(50),
	DLT_Email varchar(50)
)
Insert into DLT Values (1,'NghiaPVC','nghia@fpt.edu.vn')
Insert into DLT Values (2,'NamPVC','namhhgc00529@fpt.edu.vn')
Insert into DLT Values (3,'DungPVC','dung@fpt.edu.vn')
SELECT * FROM DLT 

create table Faculty(
	FacultyId integer primary key,
	FalcutyName varchar(50)
)
Insert into Faculty Values (1,'Math')
Insert into Faculty Values (2,'Geography')
Insert into Faculty Values (3,'Science')
SELECT * FROM Faculty

create table AssignFaculty(
	idAF int primary key,
	CourseId varchar(50) foreign key references Course(CourseId),
	FacultyId integer  foreign key references Faculty(FacultyId),
	PVC_Id integer foreign key references PVC(PVC_Id),
	DLT_Id integer foreign key references DLT(DLT_Id)
)
Insert into AssignFaculty Values (1,'C1',1,1,2)
Insert into AssignFaculty Values (2,'C1',2,3,1)
Insert into AssignFaculty Values (3,'C2',3,1,3)
select * from AssignFaculty

create table Report(
	ReportId integer primary key,
	CL_Id integer foreign key references CL(CL_Id),
	CMR_Id integer foreign key references CMR(CMR_Id)
)

create table Approved(
	ApprovedId integer primary key,
	PVC_Id integer foreign key references PVC(PVC_Id),
	CM_Id integer foreign key references CM(CM_Id)
)

create table UnApproved(
	UnApprovedId integer primary key,
	CL_id integer foreign key references CL(CL_Id)
)

create table CMR(
	CMR_Id integer primary key,
	ApprovedId integer foreign key references Approved(ApprovedId),
	UnApproved integer foreign key references UnApproved(UnApprovedId)
)


create table Staff(
	StaffId integer primary key,
	CL_Id integer foreign key references CL(CL_Id),
	CM_Id integer foreign key references CM(CM_Id),
	PVC integer foreign key references PVC(PVC_Id),
	DLT integer foreign key references DLT (DLT_Id),
	GuestId integer foreign key references Guest (GuestId)
)

create table Guest(
	GuestId integer primary key,
	GuestName varchar(50),
	GuestEmail	varchar(50)
)



