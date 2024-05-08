import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import AuthService from "../../services/auth.service";

const validationSchema = Yup.object().shape({
  email: Yup.string()
    .email('Email format is not correct!')
    .required('Email is required!'),
  username: Yup.string()
    .min(2, 'Username is too short! (min length 2)')
    .max(255, 'Username is too long! (max length 255)')
    .required('Username is required!'),
  password: Yup.string()
    .min(4, 'Password is too short! (min 4 char)')
    .max(255, 'Password is too long! (max 255 char)')
    .required('Password is required!'),
  phone: Yup.string()
    .min(9, 'Min 9 digits!')
    .max(15, 'Max 15 digits!')
    .matches(/^\+?\d+$/, 'Phone number must contain only digits and may start with a plus sign')
    .required('Phone number is required!'),
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
            phone: ''
          }}
          validationSchema={validationSchema}
          onSubmit={async (values, { setSubmitting, setErrors }) => {
            try {
              await AuthService.register(values.username, values.email, values.password, values.phone);
              navigate("/login");
              window.location.reload();
            } catch (error) {
              if (error.response && error.response.data) {
                setErrors(error.response.data);
              }
            }
            setSubmitting(false);
          }}
        >
          {({ isSubmitting }) => (
            <Form>
              <div className="form-group">
                <label htmlFor="email">Email</label>
                <Field type="email" name="email" className="form-control" />
                <ErrorMessage name="email" component="div" className="error" />
              </div>
              <div className="form-group">
                <label htmlFor="username">Username</label>
                <Field type="text" name="username" className="form-control" />
                <ErrorMessage name="username" component="div" className="error" />
              </div>
              <div className="form-group">
                <label htmlFor="password">Password</label>
                <Field type="password" name="password" className="form-control" />
                <ErrorMessage name="password" component="div" className="error" />
              </div>
              <div className="form-group">
                <label htmlFor="phone">Phone</label>
                <Field type="text" name="phone" className="form-control" />
                <ErrorMessage name="phone" component="div" className="error" />
              </div>
              <button type="submit" className="btn btn-primary btn-block" disabled={isSubmitting}>
                {isSubmitting ? 'Wait...' : 'Registration'}
              </button>
            </Form>
          )}
        </Formik>
      </div>
    </div>
  );
};

export default Registration;
