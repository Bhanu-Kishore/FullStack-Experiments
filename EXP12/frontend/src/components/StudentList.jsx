import { useState, useEffect } from 'react'
import axios from 'axios'

const StudentList = ({ onEdit }) => {
  const [students, setStudents] = useState([])

  const fetchStudents = () => {
    axios.get("http://localhost:8080/students")
      .then(response => setStudents(response.data))
      .catch(error => console.error("Error fetching students", error))
  }

  useEffect(() => {
    fetchStudents()
  }, [])

  const deleteStudent = (id) => {
    axios.delete(`http://localhost:8080/students/${id}`)
      .then(() => fetchStudents())
      .catch(error => console.error("Error deleting student", error))
  }

  return (
    <div className="card">
      <h2>Student List</h2>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Course</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {students.map(student => (
            <tr key={student.id}>
              <td>{student.name}</td>
              <td>{student.email}</td>
              <td>{student.course}</td>
              <td>
                <button className="btn-edit" onClick={() => onEdit(student)}>Update</button>
                <button className="btn-danger" onClick={() => deleteStudent(student.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

export default StudentList
