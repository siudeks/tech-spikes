import React from "react";
import { ErrorMessage } from "./ErrorMessage";
import { shallow } from "enzyme";

import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';

configure({ adapter: new Adapter() });

describe("ErrorMessage should", () => {
  fit("render Alert", () => {
    var wrapper = shallow(<ErrorMessage message="some message" debug={false} />);
    var paragraph = wrapper.find(HTMLParagraphElement);

    expect(paragraph.childAt(0).text()).toEqual("some message");
  });
});
