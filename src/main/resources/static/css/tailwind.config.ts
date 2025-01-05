import { Config } from 'tailwindcss'
import withMT from '@material-tailwind/html/utils/withMT';
import daisyui from "daisyui";
import plugin = require("tailwindcss/plugin");

const config = withMT({
    content: ["../../../jte/pages/*.jte", "../../../jte/layouts/*.jte"],
    theme: {
        extend: {
            fontFamily: {
                sans: ['Fira Sans', 'sans-serif'],
            },
            textShadow: {
                sm: '0 1px 2px var(--tw-shadow-color)',
                DEFAULT: '0 2px 4px var(--tw-shadow-color)',
                lg: '0 8px 16px var(--tw-shadow-color)',
            },
        },
    },
    daisyui: {
        themes: ["light", "emerald"]
    },
    plugins: [
        daisyui,
        plugin(function ({ matchUtilities, theme }) {
            matchUtilities(
                {
                    'text-shadow': (value) => ({
                        textShadow: value,
                    }),
                },
                { values: theme('textShadow') }
            )
        }),
    ],
} as Config);
export default config;