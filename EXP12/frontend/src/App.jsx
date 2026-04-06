import { useState, useEffect } from 'react'
import StudentList from './components/StudentList'
import AddStudent from './components/AddStudent'

function App() {
  const [students, setStudents] = useState([])
  const [editingStudent, setEditingStudent] = useState(null)

  return (
    <div className="container">
      <h1>Student Management System</h1>
      <AddStudent 
        onStudentAdded={() => setEditingStudent(null)} 
        editingStudent={editingStudent}
        setEditingStudent={setEditingStudent}
      />
      <StudentList 
        onEdit={(student) => setEditingStudent(student)} 
      />
    </div>
  )
}

export default App
