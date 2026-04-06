import { useState, useEffect } from 'react'
import axios from 'axios'

const AddStudent = ({ onStudentAdded, editingStudent, setEditingStudent }) => {
  const [student, setStudent] = useState({
    name: '',
    email: '',
    course: ''
  })

  useEffect(() => {
    if (editingStudent) {
      setStudent(editingStudent)
    } else {
      setStudent({ name: '', email: '', course: '' })
    }
  }, [editingStudent])

  const handleChange = (e) => {
    setStudent({ ...student, [e.target.name]: e.target.value })
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    if (student.id) {
      axios.put(`http://localhost:8080/students/${student.id}`, student)
        .then(() => {
          onStudentAdded()
          window.location.reload() // Refresh to see changes
        })
    } else {
      axios.post("http://localhost:8080/students", student)
        .then(() => {
          setStudent({ name: '', email: '', course: '' })
          onStudentAdded()
          window.location.reload() // Refresh to see changes
        })
    }
  }

  return (
    <div className="card">
      <h2>{student.id ? 'Edit Student' : 'Add Student'}</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Name</label>
          <input name="name" value={student.name} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Email</label>
          <input name="email" value={student.email} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Course</label>
          <input name="course" value={student.course} onChange={handleChange} required />
        </div>
        <button type="submit" className="btn-primary">
          {student.id ? 'Update' : 'Add'} Student
        </button>
        {student.id && (
          <button type="button" className="btn-danger" style={{marginLeft: '10px'}} onClick={() => setEditingStudent(null)}>
            Cancel
          </button>
        )}
      </form>
    </div>
  )
}

export default AddStudent
