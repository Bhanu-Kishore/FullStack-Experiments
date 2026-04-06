import React, { useState } from 'react';
import './StudentManager.css';

const StudentManager = () => {
    // Initial students list with at least 5 students
    const initialStudents = [
        { id: "S101", name: "Bhanu Kishore", course: "Computer Science" },
        { id: "S102", name: "Anjali Devi", course: "IT" },
        { id: "S103", name: "Varun Raj", course: "Electronics" },
        { id: "S104", name: "Priya Lakshmi", course: "Mechanical" },
        { id: "S105", name: "Rohan Kumar", course: "Civil Engineering" }
    ];

    const [students, setStudents] = useState(initialStudents);
    const [newStudent, setNewStudent] = useState({
        id: "",
        name: "",
        course: ""
    });

    // Handle input change
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewStudent({
            ...newStudent,
            [name]: value
        });
    };

    // Add new student
    const addStudent = () => {
        if (!newStudent.id || !newStudent.name || !newStudent.course) {
            alert("Please fill all fields");
            return;
        }

        // Add new student to state
        setStudents([...students, newStudent]);

        // Clear input fields
        setNewStudent({
            id: "",
            name: "",
            course: ""
        });
    };

    // Delete student
    const deleteStudent = (id) => {
        const updatedStudents = students.filter(student => student.id !== id);
        setStudents(updatedStudents);
    };

    return (
        <div className="container">
            <h1 className="title">Student Management System</h1>

            <div className="form-container">
                <input
                    type="text"
                    name="id"
                    placeholder="Student ID"
                    value={newStudent.id}
                    onChange={handleInputChange}
                    className="input-field"
                />
                <input
                    type="text"
                    name="name"
                    placeholder="Student Name"
                    value={newStudent.name}
                    onChange={handleInputChange}
                    className="input-field"
                />
                <input
                    type="text"
                    name="course"
                    placeholder="Course"
                    value={newStudent.course}
                    onChange={handleInputChange}
                    className="input-field"
                />
                <button onClick={addStudent} className="add-btn">Add Student</button>
            </div>

            <div className="table-container">
                {students.length === 0 ? (
                    <p className="no-data">No students available</p>
                ) : (
                    <table className="student-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Course</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {students.map((student) => (
                                <tr key={student.id}>
                                    <td>{student.id}</td>
                                    <td>{student.name}</td>
                                    <td>{student.course}</td>
                                    <td>
                                        <button 
                                            onClick={() => deleteStudent(student.id)} 
                                            className="delete-btn"
                                        >
                                            Delete
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                )}
            </div>
        </div>
    );
};

export default StudentManager;
