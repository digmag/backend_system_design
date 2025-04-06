import { useForm } from "@mantine/form"
import { useLazyLoginQuery } from "../api"
import { useSearchParams } from "react-router-dom"

export type SSOFormProps = {
    login: string
    password: string
}

export const useSSOForm = () => {
    const form = useForm<SSOFormProps>()
    const [trigger] = useLazyLoginQuery()
    const [params, ] = useSearchParams()
    const handleSubmit = async (vals: SSOFormProps) => {
        try{
            await trigger(vals).unwrap()
            window.location.href = params.get('redirectURI')!
        }
        catch {
            alert('Не удалось войти')
        }
    }
    return {
        form: form,
        onSubmit: form.onSubmit(handleSubmit)
    }

}