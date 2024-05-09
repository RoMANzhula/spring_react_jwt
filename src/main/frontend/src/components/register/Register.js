import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import AuthService from "../../services/auth.service";

const validationSchema = Yup.object().shape({
  email: Yup.string()
    .email('Invalid email format')
    .required('Email cannot be empty!'),
  username: Yup.string()
    .max(255, 'Message too long (limit - 255 B)')
    .required('Username cannot be empty!'),
  password: Yup.string()
    .max(255, 'Message too long (limit - 255 B)')
    .required('Password cannot be empty!'),
  phoneNumber: Yup.string()
    .min(9, 'Number is short (limit - from 9 characters)')
    .max(15, 'Number is long (limit - up to 15 characters)')
    .matches(/^\+?\d+$/, 'Phone number must contain only digits and may start with a plus sign')
    .required('Phone number cannot be empty!'),
});

const Registration = () => {
  const navigate = useNavigate();

  return (
    <div className="col-md-12">
      <div className="card card-container">
        <Formik
          initialValues={{
            email: '',
            username: '',
            password: '',
            phoneNumber: ''
          }}
          validationSchema={validationSchema}
          onSubmit={async (values, { setSubmitting, setErrors }) => {
            try {
              await AuthService.register(values.username, values.email, values.password, values.phoneNumber);
              navigate("/login");
              window.location.reload();
            } catch (error) {
              if (error.response && error.response.data) {
                const adaptedErrors = {};
                Object.keys(error.response.data).forEach(key => {
                  // Removing "ERROR" from the key and capitalizing the first letter to match form field names
                  const fieldName = key.replace("ERROR", "").charAt(0).toLowerCase() + key.slice(1, -5);
                  adaptedErrors[fieldName] = error.response.data[key];
                });
                setErrors(adaptedErrors);
              }
            }
            setSubmitting(false);
          }}
        >
          {({ isSubmitting }) => (
            <Form>
              <div className="form-group">
                <label htmlFor="email" style={{ color: 'blue' }} >Email</label>
                <Field type="email" name="email" className="form-control" />
                <ErrorMessage name="email" component="div" className="error" style={{ color: 'green' }} />
              </div>
              <div className="form-group">
                <label htmlFor="username" style={{ color: 'blue' }} >Username</label>
                <Field type="text" name="username" className="form-control" />
                <ErrorMessage name="username" component="div" className="error" style={{ color: 'green' }} />
              </div>
              <div className="form-group" style={{ color: 'blue' }} >
                <label htmlFor="password">Password</label>
                <Field type="password" name="password" className="form-control" />
                <ErrorMessage name="password" component="div" className="error" style={{ color: 'green' }} />
              </div>
              <div className="form-group" style={{ color: 'blue' }} >
                <label htmlFor="phoneNumber">Phone Number</label>
                <Field type="text" name="phoneNumber" className="form-control" />
                <ErrorMessage name="phoneNumber" component="div" className="error" style={{ color: 'green' }} />
              </div>
              <div className="text-center">
                <button type="submit" className="btn btn-primary btn-block mt-3" disabled={isSubmitting}>
                  {isSubmitting ? 'Wait...' : 'Registration'}
                </button>
              </div>
            </Form>
          )}
        </Formik>
      </div>
    </div>
  );
};

export default Registration;

