import { Stack } from "@fluentui/react";
import { RightBottomPanel } from "./RightBottomPanel";
import { RightMiddlePanel } from "./RightMiddlePanel";
import { RightTopPanel } from "./RightTopPanel";

export const RightPanel: React.FC<{}> = props => {

    return (
        < Stack
            styles={{
                root: {
                    width: "100%",
                    height: "100%",
                },
            }}
        >
            <Stack.Item verticalFill>
                <Stack
                    styles={{
                        root: {
                            height: "100%",
                            width: "100%",
                            background: "#65A3DB",
                        },
                    }}
                >
                    <Stack.Item>
                        <RightTopPanel />
                    </Stack.Item>
                    <Stack.Item
                        verticalFill
                        styles={{
                            root: {
                                height: "100%",
                                overflowY: "auto",
                                overflowX: "auto",
                            },
                        }}
                    >
                        <RightMiddlePanel />
                    </Stack.Item>
                    <Stack.Item align="center">
                        <RightBottomPanel />
                    </Stack.Item>
                </Stack>
            </Stack.Item>
        </Stack >);
}