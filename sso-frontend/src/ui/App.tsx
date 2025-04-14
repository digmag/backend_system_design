import { Button, Container, Flex, PasswordInput, TextInput, Title } from "@mantine/core";
import { useSSOForm } from "../hook"


function App() {
  const {form, onSubmit} = useSSOForm();
  return (
    <Container fluid>
      <Title>Starbly SSO</Title>
      <form onSubmit={onSubmit}>
        <Flex direction='column' gap='md'>
          <TextInput {...form.getInputProps('email')} label='email' key={form.key('email')} required/>
          <PasswordInput label='password' {...form.getInputProps('password')} key={form.key('password')} required/>
          <Button type='submit'>Войти</Button>
        </Flex>
      </form>
    </Container>
  )
}

export default App
