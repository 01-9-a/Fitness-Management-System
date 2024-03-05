DROP TABLE FollowPlan CASCADE CONSTRAINTS;
DROP TABLE UseEquipment CASCADE CONSTRAINTS;
DROP TABLE WorkoutPlan CASCADE CONSTRAINTS;
DROP TABLE Strength CASCADE CONSTRAINTS;
DROP TABLE Cardio CASCADE CONSTRAINTS;
DROP TABLE Equipment CASCADE CONSTRAINTS;
DROP TABLE Engineer CASCADE CONSTRAINTS;
DROP TABLE BookClass CASCADE CONSTRAINTS;
DROP TABLE Class CASCADE CONSTRAINTS;
DROP TABLE Instructor CASCADE CONSTRAINTS;
DROP TABLE Payment CASCADE CONSTRAINTS;
DROP TABLE Member CASCADE CONSTRAINTS;
DROP TABLE Locker CASCADE CONSTRAINTS;
DROP TABLE RequireEquipment CASCADE CONSTRAINTS;


-- create the tables in the correct order
CREATE TABLE Locker (
                        LID             VARCHAR(4),
                        Lsize            VARCHAR(255),
                        LockerStatus    VARCHAR(255),
                        LockerHourlyFee FLOAT,
                        PRIMARY KEY (LID)
);

CREATE TABLE Member (
                        MID          VARCHAR(10) PRIMARY KEY,
                        MemberName   VARCHAR(255),
                        Address      VARCHAR(255),
                        MemberPhone  VARCHAR(255),
                        MembershipSD DATE NOT NULL,
                        MembershipED DATE NOT NULL,
                        LID          VARCHAR(4),
                        UNIQUE (MemberName, MemberPhone),
                        FOREIGN KEY (LID) REFERENCES Locker(LID)
);

CREATE TABLE Payment (
                         PID         VARCHAR(10) PRIMARY KEY,
                         Amount      FLOAT,
                         PaymentDate DATE,
                         MID         VARCHAR(10) NOT NULL,
                         FOREIGN KEY (MID) REFERENCES Member(MID)
);
CREATE TABLE Instructor
(
    IID                 VARCHAR(5),
    InstructorName      VARCHAR(255),
    InstructorPhone#    VARCHAR(255),
    Expertise           VARCHAR(255),
    InstructorHourlyFee FLOAT,
    PRIMARY KEY (IID),
    UNIQUE (InstructorName, InstructorPhone#)
);

CREATE TABLE Class
(
    IID         VARCHAR(5) NOT NULL,
    Room#       INT,
    ClassName   VARCHAR(255),
    ClassDate   DATE,
    MaxCapacity INT,
    Duration    INT,
    PRIMARY KEY (IID, ClassDate, ClassName),
    FOREIGN KEY (IID) REFERENCES Instructor
);

CREATE TABLE BookClass
(
    MID       VARCHAR(10),
    IID       VARCHAR(5),
    ClassName VARCHAR(255),
    ClassDate DATE,
    PRIMARY KEY (MID, IID, ClassName, ClassDate),
    FOREIGN KEY (MID) REFERENCES Member,
    FOREIGN KEY (IID, ClassName, ClassDate) REFERENCES Class(IID, ClassName, ClassDate)
);
CREATE TABLE Engineer
(
    EngineerName    VARCHAR(255),
    EngineerPhone#  VARCHAR(255),
    EngineerAddress VARCHAR(255),
    PRIMARY KEY (EngineerName, EngineerPhone#)
);

CREATE TABLE Equipment
(
    EquipID        VARCHAR(5),
    EquipName      VARCHAR(255),
    EquipStatus    VARCHAR(255),
    EngineerName   VARCHAR(255) NOT NULL,
    EngineerPhone# VARCHAR(255) NOT NULL,
    PRIMARY KEY (EquipID),
    FOREIGN KEY (EngineerName, EngineerPhone#) REFERENCES Engineer (EngineerName, EngineerPhone#)
);

CREATE TABLE Cardio
(
    EquipID    VARCHAR(5),
    SpeedRange INT,
    PRIMARY KEY (EquipID)
);

CREATE TABLE Strength
(
    EquipID     VARCHAR(5),
    WeightRange INT,
    PRIMARY KEY (EquipID)
);

CREATE TABLE UseEquipment
(
    MID     VARCHAR(10),
    EquipID VARCHAR(5),
    PRIMARY KEY (MID),
    FOREIGN KEY (EquipID) REFERENCES Equipment,
    FOREIGN KEY (MID) REFERENCES Member
);

CREATE TABLE WorkoutPlan
(
    PID             VARCHAR(5),
    wDescription     VARCHAR(255),
    PlanFee         FLOAT,
    DifficultyLevel VARCHAR(255),
    PlanSD          DATE,
    PlanED          DATE,
    IID             VARCHAR(5) NOT NULL,
    PRIMARY KEY (PID),
    FOREIGN KEY (IID) REFERENCES Instructor
);

CREATE TABLE FollowPlan
(
    MID VARCHAR(10),
    PID VARCHAR(5),
    PRIMARY KEY (MID, PID),
    FOREIGN KEY (MID) REFERENCES Member,
    FOREIGN KEY (PID) REFERENCES WorkoutPlan(PID)
);

CREATE TABLE RequireEquipment
(
    EquipID VARCHAR(10),
    PID     VARCHAR(5),
    PRIMARY KEY (EquipID, PID),
    FOREIGN KEY (EquipID) REFERENCES Equipment,
    FOREIGN KEY (PID) REFERENCES WorkoutPlan (PID)
);

INSERT INTO	Locker(LID, lSize, LockerStatus, LockerHourlyFee) VALUES ('L001', 'big', 'unavailable', 4.5);
INSERT INTO Locker(LID, lSize, LockerStatus, LockerHourlyFee) VALUES ('L002', 'small', 'unavailable', 2.5);
INSERT INTO Locker(LID, lSize, LockerStatus, LockerHourlyFee) VALUES ('L003', 'medium', 'available', 3.5);
INSERT INTO Locker(LID, lSize, LockerStatus, LockerHourlyFee) VALUES ('L004', 'small', 'available', 2.5);
INSERT INTO Locker(LID, lSize, LockerStatus, LockerHourlyFee) VALUES ('L005', 'medium', 'available', 3.5);

INSERT INTO	Member(MID, MemberName, Address, MemberPhone, MembershipSD, MembershipED, LID) VALUES ('M000000001', 'Alice', '2943 Williams Rd', '0000000001', TO_DATE('2022-01-02', 'YYYY-MM-DD'), TO_DATE('2023-01-02', 'YYYY-MM-DD'), NULL);
INSERT INTO	Member(MID, MemberName, Address, MemberPhone, MembershipSD, MembershipED, LID) VALUES ('M000000002', 'Tom', '1248 No.3 Rd', '7781234444', TO_DATE('2022-01-02', 'YYYY-MM-DD'), TO_DATE('2023-01-02', 'YYYY-MM-DD'), 'L001');
INSERT INTO	Member(MID, MemberName, Address, MemberPhone, MembershipSD, MembershipED, LID) VALUES ('M000000003', 'David', '3244 No,2 Rd', '6043859382', TO_DATE('2022-01-02', 'YYYY-MM-DD'), TO_DATE('2023-01-02', 'YYYY-MM-DD'), NULL);
INSERT INTO	Member(MID, MemberName, Address, MemberPhone, MembershipSD, MembershipED, LID) VALUES ('M000000004', 'Jerry', '123 First Ave', '6041234567', TO_DATE('2022-01-02', 'YYYY-MM-DD'), TO_DATE('2023-01-02', 'YYYY-MM-DD'), 'L002');
INSERT INTO	Member(MID, MemberName, Address, MemberPhone, MembershipSD, MembershipED, LID) VALUES ('M000000005', 'Leo', '1111 Second Ave', '6041114385', TO_DATE('2022-01-03', 'YYYY-MM-DD'), TO_DATE('2023-01-03', 'YYYY-MM-DD'), NULL);
INSERT INTO	Member(MID, MemberName, Address, MemberPhone, MembershipSD, MembershipED, LID) VALUES ('M000000006', 'Chloe', '1022 Third Ave', '7849384821', TO_DATE('2022-01-04', 'YYYY-MM-DD'), TO_DATE('2023-01-05', 'YYYY-MM-DD'), NULL);

INSERT INTO	Payment(PID, Amount, PaymentDate, MID) VALUES ('L000000001', 295.53, TO_DATE('2022-01-02', 'YYYY-MM-DD'), 'M000000001');
INSERT INTO	Payment(PID, Amount, PaymentDate, MID) VALUES ('L000000002', 3852.4, TO_DATE('2022-01-02', 'YYYY-MM-DD'), 'M000000002');
INSERT INTO	Payment(PID, Amount, PaymentDate, MID) VALUES ('L000000003', 484.58, TO_DATE('2022-01-02', 'YYYY-MM-DD'), 'M000000003');
INSERT INTO	Payment(PID, Amount, PaymentDate, MID) VALUES ('L000000004', 1200, TO_DATE('2022-01-02', 'YYYY-MM-DD'), 'M000000004');
INSERT INTO	Payment(PID, Amount, PaymentDate, MID) VALUES ('L000000005', 2004.5, TO_DATE('2022-01-03', 'YYYY-MM-DD'), 'M000000005');

INSERT INTO	Instructor(IID, InstructorName, InstructorPhone#, Expertise, InstructorHourlyFee) VALUES ('I0001', 'Andrea', 7781234555, 'nutrition principles, group fitness', 90);
INSERT INTO	Instructor(IID, InstructorName, InstructorPhone#, Expertise, InstructorHourlyFee) VALUES ('I0002', 'Jerry', 6041485738, 'functional training, strength training, HIIT', 98);
INSERT INTO	Instructor(IID, InstructorName, InstructorPhone#, Expertise, InstructorHourlyFee) VALUES ('I0003', 'Amy', 6048394736, 'group fitness, functional training', 80);
INSERT INTO	Instructor(IID, InstructorName, InstructorPhone#, Expertise, InstructorHourlyFee) VALUES ('I0004', 'Lily', 7781475836, 'group fitness, flexibility and mobility', 95);
INSERT INTO	Instructor(IID, InstructorName, InstructorPhone#, Expertise, InstructorHourlyFee) VALUES ('I0005', 'Tom', 7784448583, 'strength training, nutrition principles', 100);

INSERT INTO	Class(IID, Room#, ClassName, ClassDate, MaxCapacity, Duration) VALUES ('I0001', 2, 'Yoga', TO_DATE('2022-03-01', 'YYYY-MM-DD'), 30, 120);
INSERT INTO	Class(IID, Room#, ClassName, ClassDate, MaxCapacity, Duration) VALUES ('I0002', 1, 'Boxing', TO_DATE('2022-03-22', 'YYYY-MM-DD'), 2, 60);
INSERT INTO	Class(IID, Room#, ClassName, ClassDate, MaxCapacity, Duration) VALUES ('I0003', 4, 'Yoga', TO_DATE('2022-03-05', 'YYYY-MM-DD'), 25, 90);
INSERT INTO	Class(IID, Room#, ClassName, ClassDate, MaxCapacity, Duration) VALUES ('I0003', 4, 'Yoga', TO_DATE('2022-03-04', 'YYYY-MM-DD'), 30, 120);
INSERT INTO	Class(IID, Room#, ClassName, ClassDate, MaxCapacity, Duration) VALUES ('I0005', 8, 'Boxing', TO_DATE('2022-03-21', 'YYYY-MM-DD'), 4, 90);

INSERT INTO	BookClass(MID, IID, ClassName, ClassDate) VALUES ('M000000001', 'I0001', 'Yoga', TO_DATE('2022-03-01', 'YYYY-MM-DD'));
INSERT INTO	BookClass(MID, IID, ClassName, ClassDate) VALUES ('M000000002', 'I0003', 'Yoga', TO_DATE('2022-03-04', 'YYYY-MM-DD'));
INSERT INTO	BookClass(MID, IID, ClassName, ClassDate) VALUES ('M000000002', 'I0002', 'Boxing', TO_DATE('2022-03-22', 'YYYY-MM-DD'));
INSERT INTO	BookClass(MID, IID, ClassName, ClassDate) VALUES ('M000000004', 'I0002', 'Boxing', TO_DATE('2022-03-22', 'YYYY-MM-DD'));
INSERT INTO	BookClass(MID, IID, ClassName, ClassDate) VALUES ('M000000005', 'I0005', 'Boxing', TO_DATE('2022-03-21', 'YYYY-MM-DD'));

INSERT INTO	Engineer(EngineerName, EngineerPhone#, EngineerAddress) VALUES ('Wilson', 7788888484, '444 No.1 Rd');
INSERT INTO	Engineer(EngineerName, EngineerPhone#, EngineerAddress) VALUES ('Tom', 7781485936, '2454 Dunbar St');
INSERT INTO	Engineer(EngineerName, EngineerPhone#, EngineerAddress) VALUES ('Jane', 6048686838, '4777 Ackroyd Rd');
INSERT INTO	Engineer(EngineerName, EngineerPhone#, EngineerAddress) VALUES ('Judy', 6048686829, '4854 Buswell St');
INSERT INTO	Engineer(EngineerName, EngineerPhone#, EngineerAddress) VALUES ('John', 6043177759, '588 Allison Rd');

INSERT INTO Equipment(EquipID, EquipName, EquipStatus, EngineerName, EngineerPhone#) VALUES ('E0001', 'stationary bicycle', 'unavail', 'Wilson', 7788888484);
INSERT INTO Equipment(EquipID, EquipName, EquipStatus, EngineerName, EngineerPhone#) VALUES ('E0002', 'leg press machine', 'unavail', 'Tom', 7781485936);
INSERT INTO Equipment(EquipID, EquipName, EquipStatus, EngineerName, EngineerPhone#) VALUES ('E0003', 'elliptical trainer', 'available', 'Jane', 6048686838);
INSERT INTO Equipment(EquipID, EquipName, EquipStatus, EngineerName, EngineerPhone#) VALUES ('E0004', 'treadmill', 'available', 'Jane', 6048686838);
INSERT INTO Equipment(EquipID, EquipName, EquipStatus, EngineerName, EngineerPhone#) VALUES ('E0005', 'chest fly machine', 'available', 'Judy', 6048686829);
INSERT INTO Equipment(EquipID, EquipName, EquipStatus, EngineerName, EngineerPhone#) VALUES ('E0006', 'stationary bicycle', 'unavail', 'Wilson', 7788888484);
INSERT INTO Equipment(EquipID, EquipName, EquipStatus, EngineerName, EngineerPhone#) VALUES ('E0007', 'leg press machine', 'unavail', 'Tom', 7781485936);
INSERT INTO Equipment(EquipID, EquipName, EquipStatus, EngineerName, EngineerPhone#) VALUES ('E0008', 'elliptical trainer', 'available', 'Jane', 6048686838);
INSERT INTO Equipment(EquipID, EquipName, EquipStatus, EngineerName, EngineerPhone#) VALUES ('E0009', 'pull-up bar', 'available', 'Jane', 6048686838);
INSERT INTO Equipment(EquipID, EquipName, EquipStatus, EngineerName, EngineerPhone#) VALUES ('E0010', 'chest fly machine', 'available', 'Judy', 6048686829);

INSERT INTO Cardio(EquipID, SpeedRange) VALUES ('E0001', 500);
INSERT INTO Cardio(EquipID, SpeedRange) VALUES ('E0003', 110);
INSERT INTO Cardio(EquipID, SpeedRange) VALUES ('E0004', 120);
INSERT INTO Cardio(EquipID, SpeedRange) VALUES ('E0006', 120);
INSERT INTO Cardio(EquipID, SpeedRange) VALUES ('E0008', 200);

INSERT INTO Strength(EquipID, WeightRange) VALUES ('E0002', 250);
INSERT INTO Strength(EquipID, WeightRange) VALUES ('E0005', 300);
INSERT INTO Strength(EquipID, WeightRange) VALUES ('E0007', 400);
INSERT INTO Strength(EquipID, WeightRange) VALUES ('E0009', 210);
INSERT INTO Strength(EquipID, WeightRange) VALUES ('E0010', 230);


INSERT INTO UseEquipment(MID, EquipID) VALUES ('M000000001', 'E0001');
INSERT INTO UseEquipment(MID, EquipID) VALUES ('M000000002', 'E0003');
INSERT INTO UseEquipment(MID, EquipID) VALUES ('M000000003', 'E0004');
INSERT INTO UseEquipment(MID, EquipID) VALUES ('M000000004', 'E0009');
INSERT INTO UseEquipment(MID, EquipID) VALUES ('M000000005', 'E0010');


INSERT INTO WorkoutPlan(PID, wDescription, PlanFee, DifficultyLevel, PlanSD, PlanED, IID) VALUES ('P0001', 'ride stationary bicycle', 15, 'easy', TO_DATE('2022-01-02', 'YYYY-MM-DD'), TO_DATE('2022-01-02', 'YYYY-MM-DD'), 'I0001');
INSERT INTO WorkoutPlan(PID, wDescription, PlanFee, DifficultyLevel, PlanSD, PlanED, IID) VALUES ('P0002', 'use leg press machine', 20, 'hard', TO_DATE('2022-04-02', 'YYYY-MM-DD'), TO_DATE('2022-08-18', 'YYYY-MM-DD'), 'I0002');
INSERT INTO WorkoutPlan(PID, wDescription, PlanFee, DifficultyLevel, PlanSD, PlanED, IID) VALUES ('P0003', 'use resistance band', 15, 'easy', TO_DATE('2022-10-03', 'YYYY-MM-DD'), TO_DATE('2022-11-22', 'YYYY-MM-DD'), 'I0001');
INSERT INTO WorkoutPlan(PID, wDescription, PlanFee, DifficultyLevel, PlanSD, PlanED, IID) VALUES ('P0004', 'lift 50lbs', 20, 'hard', TO_DATE('2022-01-01', 'YYYY-MM-DD'), TO_DATE('2022-05-01', 'YYYY-MM-DD'), 'I0003');
INSERT INTO WorkoutPlan(PID, wDescription, PlanFee, DifficultyLevel, PlanSD, PlanED, IID) VALUES ('P0005', 'lift 65lbs', 20, 'hard', TO_DATE('2022-02-03', 'YYYY-MM-DD'), TO_DATE('2023-01-01', 'YYYY-MM-DD'), 'I0005');

INSERT INTO FollowPlan(MID, PID) VALUES ('M000000001', 'P0001');
INSERT INTO FollowPlan(MID, PID) VALUES ('M000000001', 'P0002');
INSERT INTO FollowPlan(MID, PID) VALUES ('M000000004', 'P0003');
INSERT INTO FollowPlan(MID, PID) VALUES ('M000000001', 'P0004');
INSERT INTO FollowPlan(MID, PID) VALUES ('M000000005', 'P0005');

INSERT INTO RequireEquipment(EquipID, PID) VALUES ('E0001', 'P0001');
INSERT INTO RequireEquipment(EquipID, PID) VALUES ('E0002', 'P0002');
INSERT INTO RequireEquipment(EquipID, PID) VALUES ('E0003', 'P0003');
INSERT INTO RequireEquipment(EquipID, PID) VALUES ('E0004', 'P0004');
INSERT INTO RequireEquipment(EquipID, PID) VALUES ('E0005', 'P0004');
